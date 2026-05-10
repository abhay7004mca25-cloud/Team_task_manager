# Team Task Manager

A full-stack collaborative Team Task Management Web Application built with Spring Boot and React.

## Features

- **User Authentication:** Secure JWT-based login and signup.
- **Role-Based Access:** 
  - **Admins:** Create projects, manage users and tasks.
  - **Members:** View and update assigned tasks.
- **Project Management:** Create projects and organize tasks by team.
- **Task Management:** Create, assign, and track tasks (To Do, In Progress, Done).
- **Dashboard:** Analytics and insights on task progress and overdue items.

## Technologies Used

- **Backend:** Java 17, Spring Boot 3, Spring Security (JWT), Spring Data JPA, MySQL, H2 (for local dev).
- **Frontend:** React, Vite, Tailwind CSS, Framer Motion, Lucide React, Axios, React Router.

## Local Setup

### Prerequisites

- Java 17
- Node.js & npm
- Maven

### Running the Backend

1. Navigate to the `backend` directory.
2. Run `mvn spring-boot:run`
3. The backend will start on `http://localhost:8080`. (By default, it uses an in-memory H2 database. To use MySQL locally, configure `application.properties`).

### Running the Frontend

1. Navigate to the `frontend` directory.
2. Run `npm install`
3. Run `npm run dev`
4. The frontend will run on `http://localhost:5173`.

## Deployment to Railway (Mandatory Guide)

This application is configured for easy deployment on [Railway](https://railway.app/).

### 1. Provision a MySQL Database on Railway
- Create a new project on Railway.
- Add a new "Database" and select **MySQL**.

### 2. Deploy the Backend
- Add a new service from your GitHub repository (select the `backend` root folder if prompted, or use a monorepo setup).
- Add the following Environment Variables in the backend service:
  - `DATABASE_URL`: Add a reference to the MySQL database URL.
  - `JWT_SECRET`: A long secure random string.
  - `JWT_EXPIRATION`: `86400000` (1 day in milliseconds).
  - `PORT`: `8080` (or leave blank, Railway injects it).
- Railway automatically detects the Maven build and will compile and run the Spring Boot app.
- Once deployed, generate a Public Domain for the backend service (e.g., `https://backend-production.up.railway.app`).

### 3. Deploy the Frontend
- Add another new service from the same GitHub repository, but set the **Root Directory** to `/frontend`.
- Add the following Environment Variable in the frontend service:
  - `VITE_API_URL`: Your backend's public domain URL (e.g., `https://backend-production.up.railway.app`).
- Railway will detect the Vite React app, build it, and serve it.
- Generate a Public Domain for the frontend service.

## Demo Video

[Link to demo video]
