# API Spec

Swagger/OpenAPI 도입 후 주요 API 명세를 연결합니다.

## Client API

- `GET /client-api/v1/health`
- `POST /client-api/v1/auth/signup`
- `POST /client-api/v1/auth/login`
- `GET /client-api/v1/me`
- `GET /client-api/v1/pregnancy-profile/me`
- `PUT /client-api/v1/pregnancy-profile/me`
- `GET /client-api/v1/consents/me`
- `GET /client-api/v1/contents`
- `GET /client-api/v1/products`
- `GET /client-api/v1/products/{productId}`
- `GET /client-api/v1/cart`
- `POST /client-api/v1/cart`
- `POST /client-api/v1/orders`
- `GET /client-api/v1/orders`
- `POST /client-api/v1/consultations`
- `GET /client-api/v1/consultations`
- `POST /client-api/v1/inquiries`
- `GET /client-api/v1/inquiries`

## Admin API

- `GET /admin-api/v1/health`
- `POST /admin-api/v1/auth/login`
- `GET /admin-api/v1/me`
- `GET /admin-api/v1/members`
- `GET /admin-api/v1/members/{memberId}`
- `GET /admin-api/v1/consultations`
- `PATCH /admin-api/v1/consultations/{consultationId}/status`
- `GET /admin-api/v1/inquiries`
- `PATCH /admin-api/v1/inquiries/{inquiryId}/status`
- `GET /admin-api/v1/products`
- `POST /admin-api/v1/products`
- `PATCH /admin-api/v1/products/{productId}`
- `GET /admin-api/v1/orders`
- `PATCH /admin-api/v1/orders/{orderId}/status`
- `GET /admin-api/v1/audit-logs`
