# Final Report

## 진행 요약

케어 커머스 포트폴리오의 백엔드 핵심 기능, 고객 데스크톱 웹, iOS 고객 앱, 관리자 웹, 문서, AWS/CI-CD 초안을 구성했습니다.

화면 캡처 기반 이력서용 포트폴리오는 [resume-portfolio.md](resume-portfolio.md)에 정리했습니다.

## 구현된 실행 단위

- `backend`: Spring Boot API server
- `desktop-web`: 고객용 데스크톱 웹
- `ios-app`: 고객용 iOS SwiftUI 앱
- `admin-web`: 관리자 백오피스 웹

## 백엔드 구현 범위

- 사용자 회원가입/로그인
- 관리자 로그인
- JWT 기반 사용자/관리자 API 권한 분리
- 케어 프로필 등록/조회
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

### 고객 데스크톱 웹

- 상품 목록과 카테고리 필터
- 장바구니 담기와 합계 확인
- 최근 주문 내역 확인
- 상담 상태 요약
- 케어 프로필 요약

### iOS 고객 앱

- SwiftUI 하단 탭 구조
- 홈 요약
- 상품 목록과 카테고리 필터
- 장바구니와 주문 생성
- 케어 프로필 저장
- 상담 접수와 상담 상태 확인

### 관리자 웹

- 관리자 로그인
- 운영 대시보드
- 회원 목록/상세 조회
- 상품 등록/조회
- 상담/문의 목록 조회
- 마케팅 동의 고객 조회

## 화면 캡처

- 고객 데스크톱 웹: `docs/assets/portfolio/desktop-web.png`
- iOS 고객 앱: `docs/assets/portfolio/ios-app.png`
- 관리자 백오피스: `docs/assets/portfolio/admin-web.png`

## 배포/운영 구성

- AWS 수동 인프라 구성 문서
- EC2 Docker Compose 운영 파일
- Backend CD workflow 초안
- Desktop Web CD workflow 초안
- Admin Web CD workflow 초안
- iOS 릴리즈 자동화 초안
- S3/CloudFront 배포 흐름 문서

## 검증 결과

백엔드 테스트:

```txt
./gradlew test
BUILD SUCCESSFUL
```

프론트 검증:

```txt
desktop-web npm run build
admin-web npm run build
ios-app swiftc typecheck
```

현재 로컬 환경에서 두 React 웹 실행 단위의 Vite build를 통과했고, iOS Swift 소스는 Swift 타입 체크를 통과했습니다. 단, Xcode 앱이 설치되어 있지 않아 `xcodebuild` 기반 iPhone Simulator 빌드는 진행하지 못했습니다.

## 샘플 계정

```txt
관리자: admin@example.com / password123!
사용자: mother@example.com / password123!
```

로컬 프로필에서만 샘플 데이터가 적재되도록 `db/local` Flyway 경로를 분리했습니다.

## 남은 보강 사항

- AWS 실제 배포
- 배포 URL 추가
- Swagger/OpenAPI 도입
- 관리자 상담/문의 상태 변경 UI 연결
