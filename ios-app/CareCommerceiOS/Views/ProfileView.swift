import SwiftUI

struct ProfileView: View {
    @EnvironmentObject private var store: DemoStore
    @State private var status = "임신 중"
    @State private var week = 13
    @State private var expectedBirthDate = "2026-12-01"

    var body: some View {
        NavigationStack {
            Form {
                Section("케어 프로필") {
                    TextField("상태", text: $status)
                    Stepper("임신 주차 \(week)주", value: $week, in: 1...42)
                    TextField("출산 예정일", text: $expectedBirthDate)
                }

                Section {
                    Button("프로필 저장") {
                        store.profile = CareProfile(status: status, pregnancyWeek: week, expectedBirthDate: expectedBirthDate)
                        store.notice = "iOS 데모 프로필을 저장했습니다."
                    }
                }
            }
            .navigationTitle("프로필")
            .onAppear {
                status = store.profile.status
                week = store.profile.pregnancyWeek
                expectedBirthDate = store.profile.expectedBirthDate
            }
        }
    }
}
