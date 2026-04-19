# Deploy Manager

Sistema web para gestão segura de acessos remotos e monitoramento de infraestrutura de clientes de uma empresa de software.

## Problema que resolve

O time de Service Desk acessa servidores de clientes via AnyDesk, TeamViewer e NViewer. As credenciais ficavam numa planilha Excel acessível apenas a líderes. Quando um técnico precisava de acesso, dependia do líder para buscar a credencial e gerar uma senha temporária — sem rastreabilidade, sem auditoria, sem controle.

O Deploy Manager resolve isso centralizando o acesso, criptografando as credenciais e criando um fluxo de aprovação auditável.

## Funcionalidades

- Cadastro e gerenciamento de clientes com credenciais criptografadas (AES-256-GCM)
- Fluxo de solicitação e aprovação de acesso remoto com notificações em tempo real
- Dois perfis: **ADMIN** (líderes) e **USER** (técnicos)
- Auditoria completa de acessos a credenciais
- Monitoramento de containers Docker via agente Sentinel (Go)
- Autenticação JWT com access token (15 min) e refresh token (7 dias)

## Stack

| Camada | Tecnologia |
|--------|-----------|
| Backend | Java 21 + Spring Boot 4.0.5 |
| Segurança | Spring Security + Auth0 java-jwt 4.4.0 |
| ORM | Spring Data JPA + Hibernate |
| Banco | PostgreSQL 16 |
| Migrations | Flyway |
| Mensageria | Apache Kafka (modo KRaft) |
| WebSocket | Spring WebSocket + STOMP |
| Documentação | Springdoc OpenAPI 3.0.2 (Swagger UI) |
| Frontend | Angular 17 + TypeScript + Angular Material |
| Monitoramento | Sentinel (Go) |

## Perfis de usuário

### ADMIN (líderes)
- Cadastra e edita clientes
- Visualiza credenciais completas (descriptografadas sob demanda)
- Aprova ou rejeita solicitações de acesso
- Acessa logs de auditoria
- Monitora containers via Sentinel

### USER (técnicos)
- Solicita acesso a clientes com justificativa
- Visualiza apenas dados públicos dos clientes (sem senhas)
- Acompanha suas próprias solicitações
- Recebe credenciais em tempo real quando aprovado

## Fluxo de aprovação de acesso

1. Técnico abre uma solicitação via `POST /api/access-requests`
2. Sistema salva com status `PENDING` e publica evento no Kafka
3. ADMIN recebe notificação em tempo real via WebSocket
4. ADMIN aprova via `PATCH /api/access-requests/{id}/approve`
5. Sistema descriptografa as credenciais do cliente e as retorna
6. Técnico recebe as credenciais em tempo real via WebSocket
7. Acesso expira automaticamente conforme `expiresAt`

## Segurança

- Credenciais de clientes criptografadas com **AES-256-GCM** — chave via variável de ambiente, nunca no código
- Senhas de clientes nunca retornadas para usuários do tipo USER
- Todo acesso a credenciais gera um registro de auditoria (`CREDENTIAL_VIEWED`)
- Soft delete em usuários e clientes — nenhum dado é deletado fisicamente
- Técnicos só visualizam suas próprias solicitações

## Como rodar

### Pré-requisitos
- Java 21
- Docker e Docker Compose

### Subindo a infraestrutura

```bash
docker compose up -d
```

Isso sobe o PostgreSQL e o Kafka em modo KRaft.

### Variáveis de ambiente necessárias

```env
CRYPTO_SECRET_KEY=sua-chave-aes-256-aqui
JWT_SECRET=seu-segredo-jwt-aqui
```

### Rodando o backend

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.
A documentação Swagger em `http://localhost:8080/swagger-ui.html`.

## Endpoints principais

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| POST | `/api/auth/login` | Público | Login com email e senha |
| POST | `/api/auth/refresh` | Público | Renova o access token |
| GET | `/api/users` | ADMIN | Lista usuários |
| POST | `/api/users` | ADMIN | Cria usuário |
| GET | `/api/clients` | Autenticado | Lista clientes |
| POST | `/api/clients` | ADMIN | Cadastra cliente |
| GET | `/api/clients/{id}/credentials` | ADMIN | Busca credenciais (gera auditoria) |
| POST | `/api/access-requests` | USER | Cria solicitação de acesso |
| GET | `/api/access-requests/mine` | USER | Lista suas solicitações |
| GET | `/api/access-requests/pending` | ADMIN | Lista solicitações pendentes |
| PATCH | `/api/access-requests/{id}/approve` | ADMIN | Aprova e retorna credenciais |
| PATCH | `/api/access-requests/{id}/reject` | ADMIN | Rejeita com motivo |
| GET | `/api/audit` | ADMIN | Log de auditoria completo |
| GET | `/api/clients/{id}/sentinel/health` | ADMIN | Status do agente Sentinel |
| GET | `/api/clients/{id}/sentinel/containers` | ADMIN | Lista containers Docker |
