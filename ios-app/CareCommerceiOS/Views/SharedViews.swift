import SwiftUI

struct SectionHeader: View {
    let title: String
    let subtitle: String

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(subtitle)
                .font(.caption.weight(.bold))
                .foregroundStyle(Color.careMuted)
            Text(title)
                .font(.title3.weight(.bold))
                .foregroundStyle(Color.careInk)
        }
    }
}

struct ProductLine: View {
    let product: Product

    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(product.name)
                    .font(.headline)
                    .foregroundStyle(Color.careInk)
                Text(product.category.title)
                    .font(.caption)
                    .foregroundStyle(Color.careMuted)
            }
            Spacer()
            Text(won(product.price))
                .font(.headline)
                .foregroundStyle(Color.careInk)
        }
        .padding(.vertical, 6)
    }
}

struct OrderLine: View {
    let order: OrderSummary

    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(order.orderNumber)
                    .font(.headline)
                Text(order.createdAt)
                    .font(.caption)
                    .foregroundStyle(Color.careMuted)
            }
            Spacer()
            Text(order.status)
                .font(.subheadline.weight(.bold))
                .foregroundStyle(Color.careGreen)
        }
        .padding(.vertical, 6)
    }
}
