# dock-account-api

API de contas construída com Quarkus.

## Como subir (Docker)

Pré‑requisitos: Docker e Docker Compose instalados.

1. Subir os containers
   - docker compose up -d --build
2. Ver logs da aplicação
   - docker compose logs -f app
3. Parar
   - docker compose down

Base da API: http://localhost:8080/api

## Swagger / OpenAPI
- Swagger UI: http://localhost:8080/api/q/swagger-ui/
