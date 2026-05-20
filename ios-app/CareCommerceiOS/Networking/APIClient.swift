import Foundation

struct APIClient {
    var baseURL: URL
    var accessToken: String?

    init(baseURL: URL = URL(string: "http://localhost:8080")!, accessToken: String? = nil) {
        self.baseURL = baseURL
        self.accessToken = accessToken
    }

    func getProducts() async throws -> [Product] {
        try await request(path: "/client-api/v1/products")
    }

    func getOrders() async throws -> [OrderSummary] {
        try await request(path: "/client-api/v1/orders")
    }

    func getConsultations() async throws -> [ConsultationSummary] {
        try await request(path: "/client-api/v1/consultations")
    }

    private func request<T: Decodable>(path: String) async throws -> T {
        var request = URLRequest(url: baseURL.appending(path: path))
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        if let accessToken {
            request.setValue("Bearer \(accessToken)", forHTTPHeaderField: "Authorization")
        }

        let (data, response) = try await URLSession.shared.data(for: request)

        guard let httpResponse = response as? HTTPURLResponse, (200..<300).contains(httpResponse.statusCode) else {
            throw URLError(.badServerResponse)
        }

        return try JSONDecoder().decode(T.self, from: data)
    }
}
