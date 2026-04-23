# TEST ECOMMERCE MICROSERVICES
___
## 1. Technical stack

- Spring boot 3.x.x and Spring boot 4.0.5
- Config server
- Eureka discovery
- zipkin for monitoring
- Apache kafka for messaging
- PostgreSQL and mongoDb for databases
- Java Mail Sender
- Mail dev for mail testing
- keycloak for authentication
- etc
___

## 2. Services List
| Service | Description                              | Status |
|---|------------------------------------------|---|
| Discovery service | Just the discovery service               | ✅ Done |
| Customer service | Crud of customer                         | ✅ Done |
| Product service | Crud and purchase products               | ✅ Done |
| Order service  | Make orders                              | ✅ Done |
| Payment service | Implement the payment                    | ✅ Done |
| Notification service | Send the email confirmation to customers | ✅ Done |
| Gateway service | Redirect each service on a single app    | ✅ Done |

**Tools** available by making `Docker-compose up -d`