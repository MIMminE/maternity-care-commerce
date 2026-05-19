# Maternity Care Commerce

임신·출산·육아 도메인의 사용자 서비스, D2C 커머스, 관리자 운영 시스템을 통합한 백엔드 중심 포트폴리오입니다.

## 프로젝트 개요

이 프로젝트는 모성 케어 플랫폼과 임산부 전용 커머스를 운영하는 회사를 가정하고, 사용자용 모바일 웹/PWA와 관리자 백오피스, Spring Boot API 서버를 분리해 설계했습니다.

주요 목적은 단순 CRUD가 아니라 실제 현업 부서가 사용하는 운영 흐름을 백엔드에 반영하는 것입니다.

- 사용자: 산모 프로필, 상품 주문, 상담 신청
- CS/운영: 회원 상세, 산모 프로필, 상담/문의 처리
- 마케팅: 마케팅 동의 고객 필터링
- 법무/보안: 개인정보 동의 이력, 민감정보 접근 감사 로그

## 실행 단위

```txt
backend      Spring Boot API server
client-web   사용자용 모바일 웹/PWA
admin-web    관리자 백오피스 웹
```

## 기술 스택

```txt
Backend
Java 17, Spring Boot 3, Spring Security, JPA, Flyway, PostgreSQL, H2 Test DB

Frontend
React, TypeScript, Vite

Infra / DevOps
Docker, Docker Compose, Nginx, AWS EC2/RDS/S3/CloudFront/Route53/ACM, GitHub Actions

Test
JUnit 5, Spring Boot Test, MockMvc
```

## 주요 기능

```txt
Auth
- 사용자 회원가입/로그인
- 관리자 로그인
- JWT 기반 사용자/관리자 API 권한 분리

Maternity Care
- 산모 프로필 등록/조회
- 약관/개인정보/민감정보/마케팅 동의 이력 저장

Commerce
- 관리자 상품 등록/수정
- 사용자 상품 조회
- 장바구니 담기/조회
- 장바구니 기반 주문 생성
- 주문 시점 상품 가격 스냅샷 저장

Operations
- 관리자 회원 목록/상세 조회
- 회원 상세/산모 프로필 조회 감사 로그
- 상담/상품 문의 접수 및 관리자 처리
- 운영 대시보드 통계
- 마케팅 동의 고객 필터링
- 마케팅 대상 조회 감사 로그
```

## API 구분

```txt
/client-api/v1/**   사용자 웹/PWA API
/admin-api/v1/**    관리자 백오피스 API
```

## 로컬 실행

Backend 의존성 실행:

```bash
cd backend
docker compose up -d
```

Backend 테스트:

```bash
cd backend
./gradlew test
```

Backend 실행:

```bash
cd backend
./gradlew bootRun
```

Frontend 실행:

```bash
cd client-web
npm install
npm run dev

cd admin-web
npm install
npm run dev
```

로컬 포트:

```txt
Backend:     http://localhost:8080
Client Web:  http://localhost:5173
Admin Web:   http://localhost:5174
```

## 데모 계정

로컬 프로필에서는 Flyway가 데모 데이터를 함께 적재합니다.

```txt
관리자
email: admin@example.com
password: password123!

사용자
email: mother@example.com
password: password123!
```

## 추천 시연 순서

1. `backend/docker-compose.yml`로 PostgreSQL 실행
2. `backend`에서 `./gradlew bootRun` 실행
3. `admin-web`에서 관리자 로그인
4. 대시보드 통계 확인
5. 회원 상세 조회 후 산모 프로필 확인
6. 상품 목록 확인 및 상품 등록
7. 마케팅 동의 고객 조회
8. `client-web`에서 사용자 로그인
9. 산모 프로필 저장
10. 상품 조회, 장바구니 담기, 주문 생성
11. 상담 신청

## 문서

- [아키텍처](docs/architecture.md)
- [요구사항](docs/requirements.md)
- [ERD](docs/erd.md)
- [API 요약](docs/api-spec.md)
- [보안 설계](docs/security.md)
- [배포 계획](docs/deployment.md)
- [CI/CD](docs/ci-cd.md)
- [포트폴리오 요약](docs/portfolio-summary.md)

## 포트폴리오 포인트

이 프로젝트는 백엔드 포지션 지원을 목표로 하지만, 사용자 화면과 관리자 화면을 함께 구현해 API가 실제 운영 흐름에서 어떻게 쓰이는지 확인할 수 있게 구성했습니다.

특히 모성 케어 도메인에서 중요한 개인정보 동의, 민감정보 접근 로그, CS 처리, 마케팅 동의 고객 필터링을 별도 기능으로 분리해 설계했습니다.
