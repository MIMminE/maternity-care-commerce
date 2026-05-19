package com.portfolio.maternity.client.order;

import com.portfolio.maternity.domain.cart.CartItem;
import com.portfolio.maternity.domain.cart.CartItemRepository;
import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberRepository;
import com.portfolio.maternity.domain.order.Order;
import com.portfolio.maternity.domain.order.OrderItem;
import com.portfolio.maternity.domain.order.OrderItemRepository;
import com.portfolio.maternity.domain.order.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientOrderService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public ClientOrderService(
            CartItemRepository cartItemRepository,
            MemberRepository memberRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findMine(Long memberId) {
        return orderRepository.findByMemberIdOrderByIdDesc(memberId)
                .stream()
                .map(order -> OrderResponse.of(
                        order,
                        orderItemRepository.findByOrderId(order.getId()).stream()
                                .map(OrderItemResponse::from)
                                .toList()
                ))
                .toList();
    }

    @Transactional
    public OrderResponse createFromCart(Long memberId) {
        List<CartItem> cartItems = cartItemRepository.findByMemberIdOrderByIdDesc(memberId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }

        cartItems.forEach(cartItem -> {
            if (!cartItem.getProduct().isOnSale()) {
                throw new IllegalArgumentException("판매 중이 아닌 상품이 포함되어 있습니다.");
            }
            cartItem.getProduct().decreaseStock(cartItem.getQuantity());
        });

        BigDecimal totalAmount = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Order order = orderRepository.save(new Order(member, totalAmount));

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        order,
                        cartItem.getProduct(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()
                ))
                .toList();
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteByMemberId(memberId);

        return OrderResponse.of(
                order,
                orderItems.stream().map(OrderItemResponse::from).toList()
        );
    }
}

