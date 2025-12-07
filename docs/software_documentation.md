# 💻 Software Documentation: Job Management Portal

## 1\. Introduction

### 1.1 Purpose

This document provides a comprehensive technical overview and guide for the **Job Management Portal**, a desktop application designed to facilitate the connection between companies seeking talent and students seeking employment or internship opportunities.

### 1.2 Scope

The application includes features for user authentication (Company, Student, Admin), job lifecycle management (posting, searching, applying), and administrative control over users and jobs. The application is built using **Java Swing** for the frontend interface and **MySQL** for data persistence, accessed via **XAMPP**.

### 1.3 Target Audience

This documentation is intended for **Developers** (for maintenance and future development) and **System Administrators** (for deployment and configuration).

-----

## 2\. System Architecture

### 2.1 Technology Stack

| Component | Technology | Version | Purpose |
| :--- | :--- | :--- | :--- |
| **Frontend** | Java Swing (AWT/Swing) | Java SE 8+ | Graphical User Interface (GUI) |
| **Backend/Logic** | Java SE | Java SE 8+ | Business logic and data handling |
| **Database** | MySQL | Any compatible version | Data storage for jobs, users, and applications |
| **Server Environment** | XAMPP | Latest stable | Provides the Apache and MySQL environment |
| **Connectivity** | JDBC (Java Database Connectivity) | Included in JDK | Connects the Java application to the MySQL database |

### 2.2 Application Structure

The application employs a **three-tier architecture** (or MVC-like structure), separating the presentation, business logic, and data layers.

  * **Presentation Tier (GUI):** Consists of all **Swing** classes (e.g., `LoginFrame`, `JobSearchPanel`, `AdminDashboard`). Handles user input and displays data.
  * **Business Logic Tier (Service/Controller):** Consists of classes that manage the application's core functions (e.g., `JobManager`, `UserManager`, `ApplicationService`). It validates data and interacts with the Data Tier.
  * **Data Tier (DAO - Data Access Object):** Consists of classes responsible for all database operations (e.g., `UserDAO`, `JobDAO`). Uses **JDBC** to execute SQL queries against the **MySQL** database.

-----

## 3\. Installation and Setup

### 3.1 Prerequisites

The following software must be installed and configured before running the application:

  * **Java Development Kit (JDK):** Version 8 or higher.
  * **XAMPP:** Must be installed and running with **Apache** and **MySQL** modules activated.
  * **MySQL Connector/J:** The JDBC driver for MySQL must be included in the Java project's classpath.

### 3.2 Database Setup

The application requires a database named `job_portal`.

1.  **Start XAMPP:** Ensure the Apache and MySQL services are running.
2.  **Create Database:** Access the MySQL console or phpMyAdmin and execute the following SQL command:
    ```sql
    CREATE DATABASE job_portal;
    ```
3.  **Schema Creation:** Run the DDL (Data Definition Language) script (e.g., `schema.sql`) provided with the application to create the necessary tables (`users`, `jobs`, `applications`, etc.).

### 3.3 Configuration File

The database connection parameters are configured in a properties file (e.g., `config.properties`) or within a dedicated `DBConnection` class.

| Parameter | Example Value | Description |
| :--- | :--- | :--- |
| **DB\_URL** | `jdbc:mysql://localhost:3306/job_portal` | Database connection URL. |
| **DB\_USER** | `root` | Database username (default for XAMPP). |
| **DB\_PASSWORD** | *`""`* (empty string) | Database password (default for XAMPP). |

-----

## 4\. Key Features and Modules

### 4.1 User Authentication and Authorization

  * **Registration:** Separate forms for **Company** and **Student** registration.
  * **Login:** Single login form with role-based authentication.
  * **Roles:**
      * **Student:** Search, apply, track status.
      * **Company:** Post jobs, view applications.
      * **Admin:** Manage all users and jobs.

### 4.2 Job Management (Company/Admin)

  * **Job Posting:** Form to submit job details (**Title, Description, Requirements, Location, Type, Deadline**).
  * **Job Viewing/Editing:** Ability for the company to view and modify their posted jobs.

### 4.3 Job Search and Application (Student)

  * **Search Filters:** Students can search using filters for **Location**, **Role**, and **Job Type**.
  * **Application Submission:** Students can submit an application, which includes uploading a **resume/CV** (file path stored in DB).

### 4.4 Application Tracking and Notifications

  * **Status Tracking:** Students can view the status of their applications (e.g., *Pending*, *Reviewed*, *Interview*, *Rejected*).
  * **Email Notifications:** Basic integration (using JavaMail API or a simpler stub) for sending status update notifications to the student's registered email address.

-----

## 5\. Database Schema (Conceptual)

The system relies on at least the following four core tables.

  * **`users`:** Stores all user accounts (Students, Companies, Admin). Includes a `role` column.
      * *Key Fields:* `user_id` (PK), `username`, `password_hash`, `email`, `role`.
  * **`jobs`:** Stores details of all posted jobs.
      * *Key Fields:* `job_id` (PK), `company_id` (FK to `users`), `title`, `description`, `location`, `deadline`.
  * **`applications`:** Links students to jobs they have applied for.
      * *Key Fields:* `application_id` (PK), `job_id` (FK), `student_id` (FK to `users`), `resume_path`, `status`.

-----
