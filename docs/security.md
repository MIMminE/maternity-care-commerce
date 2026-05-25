# Security

## 기본 원칙

- 사용자 API와 관리자 API 권한 분리
- 관리자 역할 기반 접근 제어
- JWT 기반 stateless 인증
- OpenAPI/Swagger UI JWT Bearer 인증 검증
- 개인정보 조회 시 접근 로그 저장
- RDS 외부 접근 차단
- 운영 비밀값은 GitHub Secrets와 서버 환경변수로 관리

## 공개 API

- `GET /client-api/v1/health`
- `POST /client-api/v1/auth/signup`
- `POST /client-api/v1/auth/login`
- `GET /admin-api/v1/health`
- `POST /admin-api/v1/auth/login`
- `GET /swagger-ui/**`
- `GET /v3/api-docs/**`

그 외 `/client-api/**`는 `MEMBER` 권한, `/admin-api/**`는 관리자 역할 권한이 필요합니다.

## 관리자 역할

- `ADMIN`: 전체 관리
- `CS_MANAGER`: 상담, 문의, 회원 응대
- `OPERATION_MANAGER`: 주문, 배송, 상품 운영
- `MARKETER`: 마케팅 동의 고객 필터링, 캠페인 대상 조회
- `LEGAL_MANAGER`: 동의 이력, 개인정보 접근 로그 점검

## 관리자 API 권한 경계

| API | 허용 역할 | 목적 |
| --- | --- | --- |
| `GET /admin-api/v1/members/**` | `ADMIN`, `CS_MANAGER`, `LEGAL_MANAGER` | 회원/케어 프로필 조회 |
| `/admin-api/v1/consultations/**` | `ADMIN`, `CS_MANAGER` | 상담 처리 |
| `/admin-api/v1/inquiries/**` | `ADMIN`, `CS_MANAGER` | 상품 문의 처리 |
| `/admin-api/v1/products/**` | `ADMIN`, `OPERATION_MANAGER` | 상품 운영 |
| `GET /admin-api/v1/marketing/**` | `ADMIN`, `MARKETER`, `LEGAL_MANAGER` | 마케팅 동의 고객 조회 |
| `GET /admin-api/v1/audit-logs/**` | `ADMIN`, `LEGAL_MANAGER` | 감사 로그 점검 |
| `/admin-api/**` 기타 | `ADMIN` | 전체 관리자 기능 |

## 감사 로그 대상

- 회원 상세 조회
- 케어 프로필 조회
- 상담 상태 변경
- 상품 문의 상태 변경
- 주문 상태 변경
- 마케팅 대상 내보내기

감사 로그는 `GET /admin-api/v1/audit-logs`에서 최신순으로 확인할 수 있습니다. 이 API는 `ADMIN`과 `LEGAL_MANAGER`만 접근할 수 있습니다.

## OpenAPI 인증 검증

Swagger UI는 `http://localhost:8080/swagger-ui/index.html`에서 확인합니다.

1. `/admin-api/v1/auth/login` 또는 `/client-api/v1/auth/login`으로 토큰을 발급받습니다.
2. Swagger UI의 Authorize 버튼에 `Bearer <token>` 형식으로 입력합니다.
3. 보호 API를 호출해 role에 따른 접근 허용/거부를 확인합니다.

## 관리자 민감정보 조회 정책

관리자가 `GET /admin-api/v1/members/{memberId}`로 회원 상세와 케어 프로필을 조회할 때 `X-Audit-Reason` 헤더의 사유를 함께 남깁니다. 헤더가 없으면 기본 조회 사유를 저장합니다.

마케팅팀이 `GET /admin-api/v1/marketing/members`로 마케팅 동의 고객을 조회할 때도 `EXPORT_MARKETING_TARGETS` 감사 로그를 저장합니다.
