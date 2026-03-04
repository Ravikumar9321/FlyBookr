✈️ FlyBookr – Full Stack Flight Booking System
📌 Project Overview

FlyBookr is a full-stack Flight Booking System built using Spring Boot 3.5.5 and React.js.

The application allows users to search flights, create bookings with multiple passengers, and view booking history.
It demonstrates complete end-to-end integration between frontend, backend, and database.

This project is designed to showcase real-world full-stack development skills for a Junior Java Full Stack Developer role.

🚀 Tech Stack
🔹 Backend

Java 21

Spring Boot 3.5.5

Spring Data JPA / Hibernate

PostgreSQL

RESTful APIs

Maven

🔹 Frontend

React.js

Axios

React Router

useState & useEffect

Responsive CSS

🔹 Tools

Postman (API Testing)

Git & GitHub

🏗️ Architecture

Frontend (React) ➝ REST API (Spring Boot) ➝ PostgreSQL Database

The backend follows a clean Layered Architecture:

Controller Layer – Handles HTTP requests

Service Layer – Business logic

Repository Layer – Database interaction

✨ Features
🔍 Flight Management

View all available flights

Search flights by source and destination

Dynamic filtering in frontend

🧾 Booking Management

Create new booking

Add multiple passengers per booking

Automatic total fare calculation

View booking history

👥 Passenger Management

One-to-Many relationship (Booking → Passengers)

JPA/Hibernate entity mapping

🗄️ Database Design
Entities
1️⃣ Flight

id

flightNumber

airline

source

destination

departureTime

arrivalTime

price

2️⃣ Booking

id

bookingDate

totalAmount

flight (Many-to-One)

passengers (One-to-Many)

3️⃣ Passenger

id

name

age

gender

booking (Many-to-One)

📁 Project Structure
FlyBookr/
├── backend/ # 🔥 JAVA BACKEND (Primary)
│ ├── src/main/java/com/flybookr/
│ │ ├── entity/
│ │ ├── controller/
│ │ ├── service/
│ │ ├── repository/
│ │ └── FlightBokkingApplication.java
│ └── pom.xml
├── frontend/ # React UI (Secondary)
│ ├── src/
│ │ ├── components/
│ │ ├── App.js
│ │ └── index.js
│ └── package.json
├── doc/ # Documentation
└── README.md

🔗 REST API Endpoints
✈️ Flight APIs

| Method | Endpoint   | Description     |
| ------ | ---------- | --------------- |
| GET    | `/flight` | Get all flights |
| POST   | `/flight` | Add new flight  |


📘 Booking APIs
| Method | Endpoint    | Description        |
| ------ | ----------- | ------------------ |
| GET    | `/bookings` | Get all bookings   |
| POST   | `/bookings` | Create new booking |

🧪 How to Run the Project

 1️⃣ Configure PostgreSQL in application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/FlightManagementDB
spring.datasource.username=postgres
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

2️⃣ Backend Setup (Eclipse)
cd backend

Open in Eclipse
➡ Right-click project
➡ Run As
➡ Spring Boot App

Backend runs on:
👉 http://localhost:8080


3️⃣ Frontend Setup
cd frontend
npm install
npm start

Frontend runs on:
👉 http://localhost:3000




## 🎯 Future Enhancements
- User authentication with JWT
- Role-based access (Admin/User)
- Payment gateway integration
- Flight seat selection
- Pagination & sorting for flight search
