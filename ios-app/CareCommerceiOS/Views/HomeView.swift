import SwiftUI

struct HomeView: View {
    @EnvironmentObject private var store: DemoStore

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Card {
                        Text("오늘의 케어")
                            .font(.caption.weight(.bold))
                            .foregroundStyle(Color.careMuted)
                        Text("\(store.profile.pregnancyWeek)주차 케어를 준비했어요")
                            .font(.title2.weight(.bold))
                            .foregroundStyle(Color.careInk)
                        Text(store.notice)
                            .font(.subheadline)
                            .foregroundStyle(Color.careMuted)
                    }

                    HStack(spacing: 12) {
                        SummaryTile(title: "케어 상태", value: store.profile.status)
                        SummaryTile(title: "장바구니", value: "\(store.cart.count)개")
                    }

                    HStack(spacing: 12) {
                        SummaryTile(title: "진행 주문", value: store.orders.first?.status ?? "없음")
                        SummaryTile(title: "상담", value: store.consultations.first?.status ?? "없음")
                    }

                    Card {
                        SectionHeader(title: "추천 상품", subtitle: "자주 찾는 상품")
                        ForEach(store.products.prefix(2)) { product in
                            Button {
                                store.addToCart(product)
                            } label: {
                                ProductLine(product: product)
                            }
                            .buttonStyle(.plain)
                        }
                    }

                    Card {
                        SectionHeader(title: "최근 주문", subtitle: "진행 상태")
                        if let order = store.orders.first {
                            OrderLine(order: order)
                        } else {
                            Text("아직 주문 내역이 없습니다.")
                                .foregroundStyle(Color.careMuted)
                        }
                    }

                    Card {
                        SectionHeader(title: "상담 상태", subtitle: "최근 문의")
                        ForEach(store.consultations.prefix(2)) { consultation in
                            HStack {
                                VStack(alignment: .leading, spacing: 4) {
                                    Text(consultation.title)
                                        .font(.headline)
                                        .foregroundStyle(Color.careInk)
                                    Text(consultation.requestedAt)
                                        .font(.caption)
                                        .foregroundStyle(Color.careMuted)
                                }
                                Spacer()
                                Text(consultation.status)
                                    .font(.subheadline.weight(.bold))
                                    .foregroundStyle(Color.careGreen)
                            }
                            .padding(.vertical, 6)
                        }
                    }
                }
                .padding(20)
            }
            .background(Color.careSurface)
            .navigationTitle("Care Commerce")
        }
    }
}

private struct SummaryTile: View {
    let title: String
    let value: String

    var body: some View {
        Card {
            Text(title)
                .font(.caption.weight(.bold))
                .foregroundStyle(Color.careMuted)
            Text(value)
                .font(.title3.weight(.bold))
                .foregroundStyle(Color.careInk)
        }
    }
}
