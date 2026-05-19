package com.portfolio.maternity.client.inquiry;

import com.portfolio.maternity.domain.inquiry.Inquiry;
import com.portfolio.maternity.domain.inquiry.InquiryRepository;
import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberRepository;
import com.portfolio.maternity.domain.product.Product;
import com.portfolio.maternity.domain.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientInquiryService {

    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public ClientInquiryService(
            InquiryRepository inquiryRepository,
            MemberRepository memberRepository,
            ProductRepository productRepository
    ) {
        this.inquiryRepository = inquiryRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<InquiryResponse> findMine(Long memberId) {
        return inquiryRepository.findByMemberIdOrderByIdDesc(memberId)
                .stream()
                .map(InquiryResponse::from)
                .toList();
    }

    @Transactional
    public InquiryResponse create(Long memberId, InquiryCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Product product = request.productId() == null
                ? null
                : productRepository.findById(request.productId())
                        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        Inquiry inquiry = inquiryRepository.save(new Inquiry(
                member,
                product,
                request.title(),
                request.body()
        ));
        return InquiryResponse.from(inquiry);
    }
}

