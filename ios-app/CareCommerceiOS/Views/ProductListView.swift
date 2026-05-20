import SwiftUI

struct ProductListView: View {
    @EnvironmentObject private var store: DemoStore

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    categoryPicker

                    ForEach(store.filteredProducts) { product in
                        Card {
                            HStack(alignment: .top, spacing: 14) {
                                ProductBadge(title: product.category.title)

                                VStack(alignment: .leading, spacing: 6) {
                                    Text(product.name)
                                        .font(.headline)
                                        .foregroundStyle(Color.careInk)
                                    Text("재고 \(product.stockQuantity)개 · \(product.status)")
                                        .font(.subheadline)
                                        .foregroundStyle(Color.careMuted)
                                    Text(won(product.price))
                                        .font(.title3.weight(.bold))
                                        .foregroundStyle(Color.careInk)
                                }

                                Spacer()

                                Button("담기") {
                                    store.addToCart(product)
                                }
                                .buttonStyle(.borderedProminent)
                                .tint(Color.careGreen)
                            }
                        }
                    }
                }
                .padding(20)
            }
            .background(Color.careSurface)
            .navigationTitle("상품")
        }
    }

    private var categoryPicker: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 8) {
                ForEach(ProductCategory.allCases) { category in
                    Button {
                        store.selectedCategory = category
                    } label: {
                        Text(category.title)
                            .font(.subheadline.weight(.bold))
                            .padding(.horizontal, 14)
                            .padding(.vertical, 9)
                    }
                    .foregroundStyle(store.selectedCategory == category ? .white : .careGreen)
                    .background(store.selectedCategory == category ? Color.careGreen : Color.white)
                    .clipShape(Capsule())
                }
            }
            .padding(.vertical, 2)
        }
    }
}

private struct ProductBadge: View {
    let title: String

    var body: some View {
        Text(title)
            .font(.caption.weight(.bold))
            .foregroundStyle(Color.careGreen)
            .frame(width: 56, height: 56)
            .background(Color.careGreen.opacity(0.12))
            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
    }
}
