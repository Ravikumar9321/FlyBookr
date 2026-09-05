# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
=======
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

## 📁 Project Structure

| Path | Description |
|------|-------------|
| `FlyBookr/` | **Root Directory** |
| `backend/` | **Spring Boot API**  |
| `backend/src/main/java/com/flybookr/` | **Java Source Code** |
| `entity/` | Flight, Booking, Passenger entities |
| `controller/` | REST Controllers |
| `service/` | Business Logic |
| `repository/` | JPA Repositories |
| `FlightBokkingApplication.java` | **Main Application Class** |
| `pom.xml` | Maven Dependencies |
| `frontend/` | **React Application** |
| `frontend/src/` | React Source |
| `components/` | React Components |
| `App.js` | Main App Component |
| `index.js` | Entry Point |
| `package.json` | NPM Dependencies |
| `doc/` | **Screenshots & Documentation** |
| `README.md` | **This file** |


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
>>>>>>> a92144c1a91a16348c2dad8d136194f096ce5102
