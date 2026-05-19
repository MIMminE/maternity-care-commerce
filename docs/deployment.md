# Deployment

## Production

- `client-web`: S3 + CloudFront
- `admin-web`: S3 + CloudFront
- `backend`: EC2 + Docker + Nginx
- Database: RDS PostgreSQL
- File storage: S3
- Domain/SSL: Route53 + ACM

## 도메인 예시

```txt
app.example.com       client-web
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

## 운영 보안

- RDS Public Access 비활성화
- DB Security Group은 EC2 Security Group만 허용
- SSH는 관리자 IP만 허용
- 운영 비밀값은 GitHub Secrets 또는 EC2 `.env`로 분리
- 관리자 API 접근은 JWT role 기반으로 제한
