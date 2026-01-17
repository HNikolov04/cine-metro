# CineMetro

CineMetro is a Spring Boot backend for a cinema management and booking platform. It provides a clean domain-driven structure, REST APIs for cinema operations, orders/tickets/products, and a JWT-based authentication flow with user roles and notifications.

## Features

### Cinema Management
- Cities, cinema buildings, halls, and seats
- Movies and screenings
- Seat availability lookup by screening
- Screening overlap prevention (with cleanup buffer)
- Uniqueness checks for titles, halls, and seats

### Booking & Sales
- Orders are created from a screening + seat selection + optional products
- Tickets are created inside the order flow
- Double-booking prevention per screening and seat
- Order total calculation with discount handling

### Users & Security
- Register, login, change password
- JWT-based stateless auth
- Role-based access (ADMIN, CUSTOMER)

### Notifications (Inbox)
- Admin can create notifications for users
- Users can fetch and mark notifications as read

## Project Structure

```
server/cinemetro/src/main/java/com/cineworld/cinemetro
├── application
│   ├── dto
│   ├── mapper
│   └── service
├── domain
│   ├── enums
│   ├── exceptions
│   └── model
├── infrastructure
│   ├── config
│   └── security
├── persistence
│   ├── configuration
│   └── repository
└── webapi
    ├── controller
    └── globalexception
```

## API Overview

Base paths (non-exhaustive):

Cinema:
- `GET /api/cities`
- `GET /api/buildings`
- `GET /api/halls`
- `GET /api/seats`
- `GET /api/seats/by-screening/{screeningId}/available`
- `GET /api/movies`
- `GET /api/screenings`

Orders & Tickets:
- `POST /api/orders` (screeningId + seatIds + productIds)
- `GET /api/orders`
- `POST /api/tickets`
- `GET /api/tickets`
- `GET /api/products`

Auth & Users:
- `POST /auth/register`
- `POST /auth/login`
- `POST /auth/change-password`
- `GET /api/users` (ADMIN)

Notifications:
- `POST /api/notifications` (ADMIN)
- `GET /api/notifications/me`
- `PATCH /api/notifications/{id}/read`

Swagger UI:
- `/swagger-ui/index.html`

## Business Rules Enforced

- Movie title uniqueness
- Hall name uniqueness per building
- Seat uniqueness per hall (row + number)
- Screening overlap prevention with 15-minute cleanup buffer
- Ticket double booking prevention per screening + seat
- Seat must belong to the screening hall
- Order total computed from tickets + products + discounts

## Setup

### Prerequisites
- Java 21
- Maven
- PostgreSQL

### Configuration

Configure `application.properties` or `application.yml` with:

```
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/cinemetro
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=your-strong-secret-key
jwt.expiration=86400000
```

### Run

From the project root:

```
mvn -pl server/cinemetro spring-boot:run
```

## Testing

Run all tests:

```
mvn -pl server/cinemetro test
```

Unit tests are under `src/test/java/com/cineworld/cinemetro/unit` and integration tests under `src/test/java/com/cineworld/cinemetro/integration`.

## Roles

- `ADMIN`: full access to management endpoints
- `CUSTOMER`: standard user access

## Notes

- Auth is stateless JWT; the API expects `Authorization: Bearer <token>`.
- The codebase follows a layered architecture with per-feature DTOs/services/mappers.

## License

MIT License. See `LICENSE`.
