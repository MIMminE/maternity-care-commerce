import SwiftUI

struct SupportView: View {
    @EnvironmentObject private var store: DemoStore
    @State private var title = ""
    @State private var bodyText = ""

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Card {
                        SectionHeader(title: "상담 신청", subtitle: "배송, 상품, 케어 일정 문의")
                        TextField("상담 제목", text: $title)
                            .textFieldStyle(.roundedBorder)
                        TextField("문의 내용", text: $bodyText, axis: .vertical)
                            .lineLimit(4, reservesSpace: true)
                            .textFieldStyle(.roundedBorder)
                        Button {
                            submit()
                        } label: {
                            Text("상담 접수")
                                .frame(maxWidth: .infinity)
                        }
                        .buttonStyle(.borderedProminent)
                        .tint(Color.careGreen)
                    }

                    Card {
                        SectionHeader(title: "상담 상태", subtitle: "최근 문의")
                        ForEach(store.consultations) { consultation in
                            HStack {
                                VStack(alignment: .leading, spacing: 4) {
                                    Text(consultation.title)
                                        .font(.headline)
                                    Text(consultation.requestedAt)
                                        .font(.caption)
                                        .foregroundStyle(Color.careMuted)
                                }
                                Spacer()
                                Text(consultation.status)
                                    .font(.subheadline.weight(.bold))
                            }
                            Divider()
                        }
                    }
                }
                .padding(20)
            }
            .background(Color.careSurface)
            .navigationTitle("상담")
        }
    }

    private func submit() {
        guard title.isEmpty == false else {
            store.notice = "상담 제목을 입력해 주세요."
            return
        }

        let consultation = ConsultationSummary(
            id: (store.consultations.map(\.id).max() ?? 0) + 1,
            title: title,
            status: "접수",
            requestedAt: "2026-05-19"
        )
        store.consultations.insert(consultation, at: 0)
        title = ""
        bodyText = ""
        store.notice = "iOS 데모 상담이 접수되었습니다."
    }
}
