import SwiftUI

struct RootView: View {
    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Label("홈", systemImage: "house.fill")
                }

            ProductListView()
                .tabItem {
                    Label("상품", systemImage: "bag.fill")
                }

            CartView()
                .tabItem {
                    Label("주문", systemImage: "cart.fill")
                }

            ProfileView()
                .tabItem {
                    Label("프로필", systemImage: "person.crop.circle.fill")
                }

            SupportView()
                .tabItem {
                    Label("상담", systemImage: "message.fill")
                }
        }
        .tint(Color.careGreen)
    }
}
