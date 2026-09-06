# ✈️ FlyBookr – Full Stack Flight Booking System

A production‑ready flight booking system built with **Spring Boot + React.js**, featuring secure JWT authentication, Swagger API documentation, and PostgreSQL database integration.

---

## 🚀 Tech Stack

### Backend
- Java 21  
- Spring Boot 3.5.5  
- Spring Security (JWT Authentication & Role‑Based Access)  
- Spring Data JPA / Hibernate  
- PostgreSQL  
- Swagger/OpenAPI 3.0  
- Maven  

### Frontend
- React.js  
- Axios  
- React Router  
- useState & useEffect  
- Responsive CSS  

### Tools
- Postman (API Testing)  
- JUnit 5 & Mockito (Unit Testing)  
- Git & GitHub  

---

## 🏗️ Architecture
**Frontend (React)** ➝ **REST API (Spring Boot)** ➝ **PostgreSQL Database**

Backend follows a clean layered architecture:
- **Controller Layer** – Handles HTTP requests  
- **Service Layer** – Business logic  
- **Repository Layer** – Database interaction  

---

## ✨ Features
- 🔍 **Flight Management** – Search, filter, and view flights  
- 🧾 **Booking Management** – Create bookings with multiple passengers  
- 👥 **Passenger Management** – JPA entity mapping with relationships  
- 🔒 **Secure Authentication** – JWT login & role‑based access (Admin/User)  
- 📘 **API Documentation** – Swagger UI with JWT integration  
- 🧪 **Testing** – Unit tests with JUnit 5 & Mockito  

---

## 🗄️ Database Design
**Entities:**
- **Flight** → flightNumber, airline, source, destination, departureTime, arrivalTime, price  
- **Booking** → bookingDate, totalAmount, flight (Many‑to‑One), passengers (One‑to‑Many)  
- **Passenger** → name, age, gender, booking (Many‑to‑One)  

---

## 📁 Project Structure
| Path | Description |
|------|-------------|
| `backend/` | Spring Boot API |
| `entity/` | Flight, Booking, Passenger entities |
| `controller/` | REST Controllers |
| `service/` | Business Logic |
| `repository/` | JPA Repositories |
| `frontend/` | React Application |
| `components/` | React Components |
| `doc/` | Screenshots & Documentation |
| `README.md` | This file |

---

## 🔗 REST API Endpoints

### Flight APIs
| Method | Endpoint   | Description     |
| ------ | ---------- | --------------- |
| GET    | `/flight` | Get all flights |
| POST   | `/flight` | Add new flight  |

### Booking APIs
| Method | Endpoint    | Description        |
| ------ | ----------- | ------------------ |
| GET    | `/bookings` | Get all bookings   |
| POST   | `/bookings` | Create new booking |

---

## 🧪 How to Run Locally

### 1️⃣ Configure PostgreSQL
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/FlightManagementDB
spring.datasource.username=postgres
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
