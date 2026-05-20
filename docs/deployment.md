# Deployment

## Production

- `desktop-web`: S3 + CloudFront
- `admin-web`: S3 + CloudFront
- `ios-app`: Xcode archive 또는 TestFlight 배포
- `backend`: EC2 + Docker + Nginx
- Database: RDS PostgreSQL
- File storage: S3
- Domain/SSL: Route53 + ACM

## 도메인 예시

```txt
www.example.com       desktop-web
admin.example.com     admin-web
api.example.com       backend
```

## 백엔드 배포 흐름

```txt
GitHub Actions
  -> ./gradlew test
  -> ./gradlew bootJar
  -> Docker image build
  -> EC2 SSH
  -> docker compose pull
  -> docker compose up -d
  -> health check
```

## 프론트 배포 흐름

```txt
GitHub Actions
  -> npm install
  -> npm run build
  -> aws s3 sync dist
  -> CloudFront invalidation
```

## iOS 배포 흐름

```txt
GitHub Actions
  -> Swift typecheck
  -> Xcode archive
  -> export ipa
  -> 릴리즈 산출물 업로드
  -> 필요 시 TestFlight 배포
```

## 운영 보안

- RDS Public Access 비활성화
- DB Security Group은 EC2 Security Group만 허용
- SSH는 관리자 IP만 허용
- 운영 비밀값은 GitHub Secrets 또는 EC2 `.env`로 분리
- 관리자 API 접근은 JWT role 기반으로 제한

## 로컬 데모 데이터

`application-local.yml`에서만 `classpath:db/local` Flyway location을 추가합니다. 운영 프로필은 `classpath:db/migration`만 사용하므로 데모 계정과 샘플 데이터가 운영 DB에 적재되지 않습니다.
