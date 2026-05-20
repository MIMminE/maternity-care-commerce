import Foundation
import Combine

@MainActor
final class DemoStore: ObservableObject {
    @Published var profile = CareProfile(status: "임신 중", pregnancyWeek: 13, expectedBirthDate: "2026-12-01")
    @Published var selectedCategory: ProductCategory = .all
    @Published var products: [Product] = [
        Product(id: 1, name: "케어 바디로션", category: .bodyCare, price: 32_000, stockQuantity: 30, status: "판매 중"),
        Product(id: 2, name: "케어 샴푸", category: .hairCare, price: 28_000, stockQuantity: 25, status: "판매 중"),
        Product(id: 3, name: "케어 어메니티 세트", category: .giftSet, price: 54_000, stockQuantity: 12, status: "판매 중"),
        Product(id: 4, name: "케어 홈 릴렉스 키트", category: .giftSet, price: 68_000, stockQuantity: 8, status: "판매 중")
    ]
    @Published var cart: [CartLine] = []
    @Published var orders: [OrderSummary] = [
        OrderSummary(id: 1, orderNumber: "ORD-20260519-001", status: "배송 준비", totalAmount: 86_000, createdAt: "2026-05-19")
    ]
    @Published var consultations: [ConsultationSummary] = [
        ConsultationSummary(id: 1, title: "배송 일정 변경 문의", status: "답변 대기", requestedAt: "2026-05-19"),
        ConsultationSummary(id: 2, title: "상품 구성 문의", status: "답변 완료", requestedAt: "2026-05-16")
    ]
    @Published var notice = "데모 데이터로 iOS 고객 앱 흐름을 확인합니다."

    var filteredProducts: [Product] {
        if selectedCategory == .all {
            return products
        }

        return products.filter { $0.category == selectedCategory }
    }

    var cartTotal: Int {
        cart.reduce(0) { $0 + $1.lineAmount }
    }

    func addToCart(_ product: Product) {
        if let index = cart.firstIndex(where: { $0.product.id == product.id }) {
            cart[index].quantity += 1
        } else {
            cart.append(CartLine(product: product, quantity: 1))
        }
        notice = "\(product.name)을 장바구니에 담았습니다."
    }

    func createOrder() {
        guard cart.isEmpty == false else {
            notice = "상품을 먼저 담으면 주문 생성 흐름을 확인할 수 있습니다."
            return
        }

        let nextOrder = OrderSummary(
            id: (orders.map(\.id).max() ?? 0) + 1,
            orderNumber: "IOS-20260519-\(orders.count + 1)",
            status: "주문 생성",
            totalAmount: cartTotal,
            createdAt: "2026-05-19"
        )
        orders.insert(nextOrder, at: 0)
        cart.removeAll()
        notice = "iOS 데모 주문이 생성되었습니다."
    }
}
