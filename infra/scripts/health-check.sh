#!/usr/bin/env bash
set -euo pipefail

curl -fsS http://localhost:8080/client-api/v1/health
curl -fsS http://localhost:8080/admin-api/v1/health

