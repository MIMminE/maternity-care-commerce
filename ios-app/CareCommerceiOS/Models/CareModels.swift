import Foundation

struct CareProfile: Identifiable, Codable {
    var id = UUID()
    var status: String
    var pregnancyWeek: Int
    var expectedBirthDate: String
}

struct Product: Identifiable, Codable, Equatable {
    let id: Int
    let name: String
    let category: ProductCategory
    let price: Int
    let stockQuantity: Int
    let status: String
}

enum ProductCategory: String, Codable, CaseIterable, Identifiable {
    case all = "ALL"
    case bodyCare = "BODY_CARE"
    case hairCare = "HAIR_CARE"
    case giftSet = "GIFT_SET"

    var id: String { rawValue }

    var title: String {
        switch self {
        case .all: return "전체"
        case .bodyCare: return "바디"
        case .hairCare: return "헤어"
        case .giftSet: return "선물"
        }
    }
}

struct CartLine: Identifiable, Equatable {
    let id = UUID()
    let product: Product
    var quantity: Int

    var lineAmount: Int {
        product.price * quantity
    }
}

struct OrderSummary: Identifiable, Codable {
    let id: Int
    let orderNumber: String
    let status: String
    let totalAmount: Int
    let createdAt: String
}

struct ConsultationSummary: Identifiable, Codable {
    let id: Int
    let title: String
    let status: String
    let requestedAt: String
}
