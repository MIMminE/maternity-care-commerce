# Security

## 기본 원칙

- 사용자 API와 관리자 API 권한 분리
- 관리자 역할 기반 접근 제어
- JWT 기반 stateless 인증
- 개인정보 조회 시 접근 로그 저장
- RDS 외부 접근 차단
- 운영 비밀값은 GitHub Secrets와 서버 환경변수로 관리

## 공개 API

- `GET /client-api/v1/health`
- `POST /client-api/v1/auth/signup`
- `POST /client-api/v1/auth/login`
- `GET /admin-api/v1/health`
- `POST /admin-api/v1/auth/login`

그 외 `/client-api/**`는 `MEMBER` 권한, `/admin-api/**`는 관리자 역할 권한이 필요합니다.

## 관리자 역할

- `ADMIN`: 전체 관리
- `CS_MANAGER`: 상담, 문의, 회원 응대
- `OPERATION_MANAGER`: 주문, 배송, 상품 운영
- `MARKETER`: 마케팅 동의 고객 필터링, 캠페인 대상 조회
- `LEGAL_MANAGER`: 동의 이력, 개인정보 접근 로그 점검

## 감사 로그 대상

- 회원 상세 조회
- 산모 프로필 조회
- 상담 상태 변경
- 주문 상태 변경
- 마케팅 대상 내보내기

## 관리자 민감정보 조회 정책

관리자가 `GET /admin-api/v1/members/{memberId}`로 회원 상세와 산모 프로필을 조회할 때 `X-Audit-Reason` 헤더의 사유를 함께 남깁니다. 헤더가 없으면 기본 조회 사유를 저장합니다.
