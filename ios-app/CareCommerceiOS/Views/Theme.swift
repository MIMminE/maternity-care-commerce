import SwiftUI

extension Color {
    static let careGreen = Color(red: 49 / 255, green: 92 / 255, blue: 73 / 255)
    static let careInk = Color(red: 29 / 255, green: 36 / 255, blue: 32 / 255)
    static let careMuted = Color(red: 102 / 255, green: 113 / 255, blue: 107 / 255)
    static let careSurface = Color(red: 247 / 255, green: 249 / 255, blue: 248 / 255)
}

struct Card<Content: View>: View {
    @ViewBuilder var content: Content

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            content
        }
        .padding(16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
        .shadow(color: .black.opacity(0.06), radius: 14, y: 8)
    }
}

func won(_ value: Int) -> String {
    value.formatted(.currency(code: "KRW").precision(.fractionLength(0)))
}
