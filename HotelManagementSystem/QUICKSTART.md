# Quick Start Guide - Hotel Management System with JWT Security

## Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Git

## Setup Instructions

### 1. Database Setup
Create databases for each service:
```sql
CREATE DATABASE identitydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE inventoryservicedb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE reservationdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE guestdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE billingdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE staydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Build the Project
```bash
cd HotelManagementSystem
mvn clean install -DskipTests
```

### 3. Run Services in Order

**Terminal 1 - Service Registry (Eureka):**
```bash
mvn spring-boot:run -pl service-registry
# Access: http://localhost:8761
```

**Terminal 2 - Identity Service:**
```bash
mvn spring-boot:run -pl identity-service
# Runs on: http://localhost:8081
```

**Terminal 3 - Inventory Service:**
```bash
mvn spring-boot:run -pl inventory-service
# Runs on: http://localhost:8082
```

**Terminal 4 - Reservation Service:**
```bash
mvn spring-boot:run -pl reservation-service
# Runs on: http://localhost:8083
```

**Terminal 5 - API Gateway:**
```bash
mvn spring-boot:run -pl api-gateway1
# Runs on: http://localhost:8084 (Main entry point)
```

**Terminal 6 - Guest Service:**
```bash
mvn spring-boot:run -pl guest-service
# Runs on: http://localhost:8085
```

**Terminal 7 - Billing Service:**
```bash
mvn spring-boot:run -pl billing-service
# Runs on: http://localhost:8086
```

**Terminal 8 - Stay Service:**
```bash
mvn spring-boot:run -pl stay-service
# Runs on: http://localhost:8087
```

## API Usage

### 1. Register New User (ADMIN)
```bash
curl -X POST http://localhost:8084/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Admin",
    "email": "admin@hotel.com",
    "password": "SecurePass123!",
    "phone": "+1-234-567-8900",
    "role": "ROLE_ADMIN"
  }'
```

### 2. Register New User (REGULAR USER)
```bash
curl -X POST http://localhost:8084/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane User",
    "email": "user@hotel.com",
    "password": "SecurePass123!",
    "phone": "+1-234-567-8901",
    "role": "ROLE_USER"
  }'
```

### 3. Login & Get JWT Token
```bash
curl -X POST http://localhost:8084/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@hotel.com",
    "password": "SecurePass123!"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "email": "admin@hotel.com",
  "role": "ROLE_ADMIN",
  "userId": 1,
  "name": "John Admin"
}
```

### 4. Use Token for Protected Endpoints

Save the token in a variable:
```bash
TOKEN="your_jwt_token_here"
```

**Get All Users (ADMIN ONLY):**
```bash
curl -X GET http://localhost:8084/users/all \
  -H "Authorization: Bearer $TOKEN"
```

**Add a Room (ADMIN ONLY):**
```bash
curl -X POST http://localhost:8084/inventory/rooms/add \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "roomNumber": "101",
    "roomType": "SINGLE",
    "capacity": 1,
    "price": 150.00,
    "available": true
  }'
```

**Book a Reservation (USER & ADMIN):**
```bash
curl -X POST http://localhost:8084/reservations/book \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "guestId": 1,
    "roomId": 1,
    "checkInDate": "2025-03-15",
    "checkOutDate": "2025-03-20",
    "status": "PENDING"
  }'
```

## API Endpoints Overview

### Authentication Endpoints
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /users/register | Public | Register new user |
| POST | /users/login | Public | Get JWT token |

### Identity Service
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | /users/all | ADMIN | List all users |
| GET | /users/{id} | USER, ADMIN | Get user details |

### Inventory Service
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /inventory/rooms/add | ADMIN | Add new room |
| GET | /inventory/rooms/all | USER, ADMIN | List all rooms |
| GET | /inventory/rooms/{id} | USER, ADMIN | Get room details |

### Reservation Service
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /reservations/book | USER, ADMIN | Create reservation |
| GET | /reservations/all | ADMIN | List all reservations |
| GET | /reservations/{id} | USER, ADMIN | Get reservation details |

### Guest Service
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /requests/create | USER, ADMIN | Create service request |
| GET | /requests/all | ADMIN | List all requests |
| GET | /requests/{id} | USER, ADMIN | Get request details |
| PUT | /requests/{id}/status | ADMIN | Update request status |

### Billing Service
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /bills/generate | ADMIN | Generate bill |
| GET | /bills/all | ADMIN | List all bills |
| GET | /bills/{id} | USER, ADMIN | Get bill details |
| PUT | /bills/{id}/status | ADMIN | Update bill status |

### Stay Service
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /stays/add | ADMIN | Add stay record |
| GET | /stays/all | USER, ADMIN | List all stays |
| GET | /stays/{id} | USER, ADMIN | Get stay details |
| PUT | /stays/{id}/status | ADMIN | Update stay status |

## Key Features

✅ **JWT Authentication**
- 24-hour token expiration
- HS512 encryption algorithm
- Email and role-based claims

✅ **Role-Based Access Control**
- ROLE_ADMIN: Full access
- ROLE_USER: Limited access

✅ **Microservices Architecture**
- Service Registry (Eureka)
- API Gateway with JWT validation
- Independent databases per service
- Scalable design

✅ **Security Features**
- Password encryption (BCrypt)
- Stateless authentication
- Spring Security integration
- Custom authorization handlers

## Troubleshooting

### Error: "Unauthorized (401)"
- JWT token is missing or invalid
- Include `Authorization: Bearer <token>` header
- Check token expiration

### Error: "Access Denied (403)"
- Your role doesn't have permission
- ADMIN role required for certain operations
- Check role-based access matrix

### Error: "Service not found"
- Service hasn't started
- Service not registered with Eureka
- Check http://localhost:8761 for service status

### Error: "Database connection failed"
- MySQL not running
- Incorrect database credentials
- Database doesn't exist

### Port Already in Use
- Kill existing process on port:
```bash
# Unix/Linux/Mac
lsof -i :8084
kill -9 <PID>

# Windows
netstat -ano | findstr :8084
taskkill /PID <PID> /F
```

## Monitoring

### Eureka Dashboard
http://localhost:8761

Shows all registered services and their status.

### Check Service Health
```bash
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health
curl http://localhost:8084/actuator/health
curl http://localhost:8085/actuator/health
curl http://localhost:8086/actuator/health
curl http://localhost:8087/actuator/health
```

## Configuration Details

### JWT Settings
- **Secret:** `mySecretKeyForHotelManagementSystemThatIsLongEnoughToBeSecureAndCanBeUsedForHS512`
- **Expiration:** 86400000 ms (24 hours)
- **Algorithm:** HS512

### Database Credentials
- **Username:** root
- **Password:** helloJaanu@123
- **Host:** localhost:3306

### Service Ports
- Service Registry: 8761
- Identity Service: 8081
- Inventory Service: 8082
- Reservation Service: 8083
- API Gateway: 8084
- Guest Service: 8085
- Billing Service: 8086
- Stay Service: 8087

## Next Steps

1. Review `API_DOCUMENTATION.md` for detailed endpoint information
2. Review `SECURITY_IMPLEMENTATION.md` for architecture details
3. Set up frontend application to consume these APIs
4. Configure CORS in API Gateway for frontend integration
5. Set up SSL/TLS for production
6. Implement additional security measures (rate limiting, audit logging, etc.)

## Support & Documentation

- See `API_DOCUMENTATION.md` for complete API reference
- See `SECURITY_IMPLEMENTATION.md` for implementation details
- Check individual service `application.properties` for configuration
- Review Spring Security documentation for advanced features

---

**Happy Coding! 🚀**
