# AWS Manual Setup

1차 포트폴리오에서는 AWS 리소스를 콘솔에서 수동 생성하고, 구성 내역을 문서화합니다.

## 대상 리소스

- VPC / Subnet
- Security Group
- EC2
- RDS PostgreSQL
- S3
- CloudFront
- Route53
- ACM
- IAM

## 최소 구성 순서

1. VPC와 public/private subnet 생성
2. EC2용 Security Group 생성
3. RDS용 Security Group 생성, EC2 Security Group에서만 DB 포트 허용
4. RDS PostgreSQL 생성
5. EC2 생성 후 Docker, Docker Compose, Nginx 설치
6. S3 버킷 2개 생성: `client-web`, `admin-web`
7. CloudFront distribution 2개 생성
8. Route53 레코드 연결
9. ACM 인증서 발급
10. GitHub Secrets 등록 후 GitHub Actions CD 실행
