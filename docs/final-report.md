# Final Report

## 진행 요약

모성 케어 커머스 포트폴리오의 백엔드 핵심 기능, 사용자 웹, 관리자 웹, 문서, AWS/CI-CD 초안을 구성했습니다.

화면 캡처 기반 이력서용 포트폴리오는 [resume-portfolio.md](resume-portfolio.md)에 정리했습니다.

## 구현된 실행 단위

- `backend`: Spring Boot API server
- `client-web`: 사용자용 모바일 웹/PWA
- `admin-web`: 관리자 백오피스 웹

## 백엔드 구현 범위

- 사용자 회원가입/로그인
- 관리자 로그인
- JWT 기반 사용자/관리자 API 권한 분리
- 산모 프로필 등록/조회
- 약관/개인정보/민감정보/마케팅 동의 이력 저장
- 관리자 회원 목록/상세 조회
- 민감정보 조회 감사 로그
- 상품 등록/수정/조회
- 장바구니 담기/조회
- 장바구니 기반 주문 생성/조회
- 상담 신청/조회
- 상품 문의 등록/조회
- 관리자 상담/문의 처리
- 운영 대시보드 통계
- 마케팅 동의 고객 필터링
- 마케팅 대상 조회 감사 로그

## 프론트 구현 범위

### 사용자 웹

- 가입/로그인
- 산모 프로필 저장
- 상품 조회
- 장바구니 담기
- 주문 생성/조회
- 상담 신청

### 관리자 웹

- 관리자 로그인
- 운영 대시보드
- 회원 목록/상세 조회
- 상품 등록/조회
- 상담/문의 목록 조회
- 마케팅 동의 고객 조회

## 화면 캡처

- 사용자 홈: `docs/assets/portfolio/client-home.png`
- 사용자 상품: `docs/assets/portfolio/client-products.png`
- 사용자 상담: `docs/assets/portfolio/client-consultation.png`
- 관리자 대시보드: `docs/assets/portfolio/admin-dashboard.png`
- 관리자 회원 상세: `docs/assets/portfolio/admin-members.png`
- 관리자 상품 관리: `docs/assets/portfolio/admin-products.png`
- 관리자 마케팅: `docs/assets/portfolio/admin-marketing.png`

## 배포/운영 구성

- AWS 수동 인프라 구성 문서
- EC2 Docker Compose 운영 파일
- Backend CD workflow 초안
- Client Web CD workflow 초안
- Admin Web CD workflow 초안
- S3/CloudFront 배포 흐름 문서

## 검증 결과

백엔드 테스트:

```txt
./gradlew test
BUILD SUCCESSFUL
```

프론트 검증:

```txt
npm install
```

현재 로컬 환경에서 npm registry 연결이 장시간 대기 상태가 되어 프론트 빌드 검증은 완료하지 못했습니다. 코드와 설정 파일은 Vite/React 기준으로 작성되어 있으며, 네트워크가 가능한 환경에서 `npm install && npm run build`로 확인이 필요합니다.

## 데모 계정

```txt
관리자: admin@example.com / password123!
사용자: mother@example.com / password123!
```

로컬 프로필에서만 데모 데이터가 적재되도록 `db/local` Flyway 경로를 분리했습니다.

## 남은 보강 사항

- 프론트 빌드 검증
- AWS 실제 배포
- 화면 스크린샷 추가
- 배포 URL 추가
- Swagger/OpenAPI 도입
- 관리자 상담/문의 상태 변경 UI 연결
