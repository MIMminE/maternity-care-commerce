# CI/CD

## Backend

1. Test
2. Build jar
3. Build Docker image
4. Deploy to EC2
5. Health check

필수 GitHub Secrets:

- `EC2_HOST`
- `EC2_USER`
- `EC2_SSH_KEY`
- `DOCKER_IMAGE`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

## Web

1. Type check
2. Build static files
3. Upload to S3
4. Invalidate CloudFront cache

필수 GitHub Secrets:

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
- `AWS_REGION`
- `CLIENT_WEB_BUCKET`
- `ADMIN_WEB_BUCKET`
- `CLIENT_CLOUDFRONT_DISTRIBUTION_ID`
- `ADMIN_CLOUDFRONT_DISTRIBUTION_ID`
