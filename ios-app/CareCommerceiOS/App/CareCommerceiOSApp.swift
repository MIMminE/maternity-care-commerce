import SwiftUI

@main
struct CareCommerceiOSApp: App {
    @StateObject private var store = DemoStore()

    var body: some Scene {
        WindowGroup {
            RootView()
                .environmentObject(store)
        }
    }
}
