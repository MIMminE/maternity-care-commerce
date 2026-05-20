import SwiftUI

struct CartView: View {
    @EnvironmentObject private var store: DemoStore

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Card {
                        SectionHeader(title: "장바구니", subtitle: "\(store.cart.count)개 상품")

                        if store.cart.isEmpty {
                            Text("상품을 담으면 이곳에서 수량과 금액을 확인할 수 있습니다.")
                                .font(.subheadline)
                                .foregroundStyle(Color.careMuted)
                        } else {
                            ForEach(store.cart) { line in
                                HStack {
                                    VStack(alignment: .leading, spacing: 4) {
                                        Text(line.product.name)
                                            .font(.headline)
                                        Text("\(line.quantity)개")
                                            .font(.caption)
                                            .foregroundStyle(Color.careMuted)
                                    }
                                    Spacer()
                                    Text(won(line.lineAmount))
                                        .font(.headline)
                                }
                                Divider()
                            }
                        }

                        HStack {
                            Text("합계")
                            Spacer()
                            Text(won(store.cartTotal))
                                .font(.title3.weight(.bold))
                        }
                        .padding(.top, 4)

                        Button {
                            store.createOrder()
                        } label: {
                            Text("주문 생성")
                                .frame(maxWidth: .infinity)
                        }
                        .buttonStyle(.borderedProminent)
                        .tint(Color.careGreen)
                        .disabled(store.cart.isEmpty)
                    }

                    Card {
                        SectionHeader(title: "주문 내역", subtitle: "최근 주문")
                        ForEach(store.orders) { order in
                            OrderLine(order: order)
                        }
                    }
                }
                .padding(20)
            }
            .background(Color.careSurface)
            .navigationTitle("주문")
        }
    }
}
