# Hotel Management System - API Documentation

## Overview
This is a microservices-based Hotel Management System with Spring Security + JWT authentication. All services require valid JWT tokens for access (except login and registration endpoints).

## Authentication

### 1. User Registration
**Endpoint:** `POST /users/register`
**Access:** Public (No authentication required)
**URL:** `http://localhost:8084/users/register`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "+1-234-567-8900",
  "role": "ROLE_USER"
}
```

**Role Values:**
- `ROLE_ADMIN` - Administrator (can manage all resources)
- `ROLE_USER` - Regular user (can view and create resources)

**Response:**
```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1-234-567-8900",
  "role": "ROLE_USER",
  "enabled": true
}
```

### 2. User Login (Get JWT Token)
**Endpoint:** `POST /users/login`
**Access:** Public (No authentication required)
**URL:** `http://localhost:8084/users/login`

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwidXNlcklkIjoxLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzA4NjY2OTk5LCJleHAiOjE3MDg3NTMzOTl9...",
  "email": "john@example.com",
  "role": "ROLE_USER",
  "userId": 1,
  "name": "John Doe"
}
```

**Token Usage:**
Include the token in all subsequent requests:
```
Authorization: Bearer <your_jwt_token>
```

---

## Services & Endpoints

### Identity Service (Port 8081)
Base URL: `http://localhost:8084/users` (via API Gateway)

#### Get All Users
- **Endpoint:** `GET /users/all`
- **Access:** `ROLE_ADMIN` only
- **Description:** Get list of all users

#### Get User by ID
- **Endpoint:** `GET /users/{id}`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get specific user details

---

### Inventory Service (Port 8082)
Base URL: `http://localhost:8084/inventory` (via API Gateway)

#### Add Room
- **Endpoint:** `POST /inventory/rooms/add`
- **Access:** `ROLE_ADMIN` only
- **Description:** Add a new room to inventory
- **Request Body:**
```json
{
  "roomNumber": "101",
  "roomType": "SINGLE",
  "capacity": 1,
  "price": 100.00,
  "available": true
}
```

#### Get All Rooms
- **Endpoint:** `GET /inventory/rooms/all`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get list of all rooms

#### Get Room by ID
- **Endpoint:** `GET /inventory/rooms/{id}`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get specific room details

---

### Reservation Service (Port 8083)
Base URL: `http://localhost:8084/reservations` (via API Gateway)

#### Book Reservation
- **Endpoint:** `POST /reservations/book`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Create a new reservation
- **Request Body:**
```json
{
  "guestId": 1,
  "roomId": 1,
  "checkInDate": "2025-03-01",
  "checkOutDate": "2025-03-05",
  "status": "PENDING"
}
```

#### Get All Reservations
- **Endpoint:** `GET /reservations/all`
- **Access:** `ROLE_ADMIN` only
- **Description:** Get list of all reservations

#### Get Reservation by ID
- **Endpoint:** `GET /reservations/{id}`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get specific reservation details

---

### Guest Service (Port 8085)
Base URL: `http://localhost:8084/requests` (via API Gateway)

#### Create Service Request
- **Endpoint:** `POST /requests/create`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Create a new service request
- **Request Body:**
```json
{
  "guestId": 1,
  "requestType": "HOUSEKEEPING",
  "description": "Please clean the room",
  "status": "PENDING"
}
```

#### Get All Requests
- **Endpoint:** `GET /requests/all`
- **Access:** `ROLE_ADMIN` only
- **Description:** Get list of all service requests

#### Get Request by ID
- **Endpoint:** `GET /requests/{id}`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get specific service request details

#### Update Request Status
- **Endpoint:** `PUT /requests/{id}/status`
- **Access:** `ROLE_ADMIN` only
- **Parameters:** `status` (PENDING, IN_PROGRESS, COMPLETED)

---

### Billing Service (Port 8086)
Base URL: `http://localhost:8084/bills` (via API Gateway)

#### Generate Bill
- **Endpoint:** `POST /bills/generate`
- **Access:** `ROLE_ADMIN` only
- **Description:** Generate a new bill
- **Request Body:**
```json
{
  "reservationId": 1,
  "amount": 500.00,
  "status": "PENDING"
}
```

#### Get All Bills
- **Endpoint:** `GET /bills/all`
- **Access:** `ROLE_ADMIN` only
- **Description:** Get list of all bills

#### Get Bill by ID
- **Endpoint:** `GET /bills/{id}`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get specific bill details

#### Update Bill Status
- **Endpoint:** `PUT /bills/{id}/status`
- **Access:** `ROLE_ADMIN` only
- **Parameters:** `status` (PENDING, PAID, CANCELLED)

---

### Stay Service (Port 8087)
Base URL: `http://localhost:8084/stays` (via API Gateway)

#### Add Stay
- **Endpoint:** `POST /stays/add`
- **Access:** `ROLE_ADMIN` only
- **Description:** Add a new stay record
- **Request Body:**
```json
{
  "reservationId": 1,
  "checkInDate": "2025-03-01",
  "checkOutDate": "2025-03-05",
  "status": "ACTIVE"
}
```

#### Get All Stays
- **Endpoint:** `GET /stays/all`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get list of all stays

#### Get Stay by ID
- **Endpoint:** `GET /stays/{id}`
- **Access:** `ROLE_USER`, `ROLE_ADMIN`
- **Description:** Get specific stay details

#### Update Stay Status
- **Endpoint:** `PUT /stays/{id}/status`
- **Access:** `ROLE_ADMIN` only
- **Parameters:** `status` (ACTIVE, COMPLETED, CANCELLED)

---

## Testing with cURL

### 1. Register a User
```bash
curl -X POST http://localhost:8084/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Admin User",
    "email": "admin@example.com",
    "password": "admin123",
    "phone": "+1-234-567-8900",
    "role": "ROLE_ADMIN"
  }'
```

### 2. Login and Get Token
```bash
curl -X POST http://localhost:8084/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "admin123"
  }'
```

### 3. Use Token to Access Protected Endpoint
```bash
curl -X GET http://localhost:8084/users/all \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

## Role-Based Access Summary

| Endpoint | ADMIN | USER |
|----------|-------|------|
| POST /users/register | ✓ | ✓ |
| POST /users/login | ✓ | ✓ |
| GET /users/all | ✓ | ✗ |
| GET /users/{id} | ✓ | ✓ |
| POST /inventory/rooms/add | ✓ | ✗ |
| GET /inventory/rooms/** | ✓ | ✓ |
| POST /reservations/book | ✓ | ✓ |
| GET /reservations/all | ✓ | ✗ |
| GET /reservations/{id} | ✓ | ✓ |
| POST /requests/create | ✓ | ✓ |
| GET /requests/all | ✓ | ✗ |
| GET /requests/{id} | ✓ | ✓ |
| PUT /requests/{id}/status | ✓ | ✗ |
| POST /bills/generate | ✓ | ✗ |
| GET /bills/all | ✓ | ✗ |
| GET /bills/{id} | ✓ | ✓ |
| PUT /bills/{id}/status | ✓ | ✗ |
| POST /stays/add | ✓ | ✗ |
| GET /stays/** | ✓ | ✓ |
| PUT /stays/{id}/status | ✓ | ✗ |

---

## Architecture Overview

```
┌─────────────────────────────────────┐
│     Client/Frontend                 │
└────────────┬────────────────────────┘
             │ HTTP/REST
             ▼
┌─────────────────────────────────────┐
│  API Gateway (Port 8084)            │
│  - JWT Validation                   │
│  - Route Management                 │
└────────────┬────────────────────────┘
             │
    ┌────────┼────────┬────────┬────────┐
    │        │        │        │        │
    ▼        ▼        ▼        ▼        ▼
┌────────┐ ┌──────┐ ┌────────┐ ┌─────┐ ┌────────┐
│Identity│ │Invent│ │Reserv.│ │Guest│ │Billing │ ┌──────┐
│Service │ │Service│ │Service│ │Serv.│ │Service │ │Stay  │
│(8081)  │ │(8082) │ │(8083) │ │(8085)│ │(8086)  │ │Serv. │
└────────┘ └──────┘ └────────┘ └─────┘ └────────┘ │(8087)│
                                                  └──────┘
    │        │        │        │        │        │
    └────────┼────────┼────────┼────────┼────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  Service Registry (Eureka)          │
│  Port 8761                          │
└─────────────────────────────────────┘
```

---

## JWT Token Structure

The JWT token contains the following claims:
- `sub`: Email address
- `userId`: User ID
- `role`: User role (ROLE_ADMIN or ROLE_USER)
- `iat`: Issued at time
- `exp`: Expiration time (24 hours from issue)

---

## Error Responses

### Unauthorized (401)
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "path": "/users/all"
}
```

### Forbidden (403)
```json
{
  "status": 403,
  "error": "Access Denied",
  "message": "Access is denied",
  "path": "/bills/generate"
}
```

### Not Found (404)
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found"
}
```

---

## Configuration Files

All services share the same JWT configuration:
- **JWT Secret:** `mySecretKeyForHotelManagementSystemThatIsLongEnoughToBeSecureAndCanBeUsedForHS512`
- **JWT Expiration:** 86400000 milliseconds (24 hours)
- **Algorithm:** HS512

Each service has its own database:
- identity-service: `identitydb`
- inventory-service: `inventoryservicedb`
- reservation-service: `reservationdb`
- guest-service: `guestdb`
- billing-service: `billingdb`
- stay-service: `staydb`

---

## Running the System

1. **Start Service Registry (Eureka)**
   ```bash
   mvn spring-boot:run -pl service-registry
   ```
   Access at: http://localhost:8761

2. **Start All Microservices**
   ```bash
   mvn clean install
   mvn spring-boot:run -pl identity-service
   mvn spring-boot:run -pl inventory-service
   mvn spring-boot:run -pl reservation-service
   mvn spring-boot:run -pl guest-service
   mvn spring-boot:run -pl billing-service
   mvn spring-boot:run -pl stay-service
   ```

3. **Start API Gateway**
   ```bash
   mvn spring-boot:run -pl api-gateway1
   ```
   Access at: http://localhost:8084

4. **Test the API**
   - Register and login to get JWT token
   - Use token in Authorization header for all protected endpoints
   - Admin and User roles have different access levels
