# Job Management Portal

A desktop application built with Java Swing that connects companies seeking talent with students seeking employment or internship opportunities.

## Prerequisites

- **Java Development Kit (JDK):** Version 8 or higher
- **Maven:** For building the project
- **XAMPP:** MySQL server running on localhost:3306
- **MySQL:** For database storage

## Installation and Setup

### 1. Database Setup

1. Start XAMPP and ensure MySQL is running
2. Create the database and tables by running the SQL script:
   ```bash
   mysql -u root < docs/schema.sql
   ```
   This creates the `job_portal` database with all required tables.

### 2. Build the Project

```bash
cd swing-job
mvn clean install
```

This will download all dependencies and compile the application.

### 3. Run the Application

**Option 1: Using Maven (Recommended)**
```bash
mvn exec:java "-Dexec.mainClass=com.example.App"
```

**Option 2: Using JAR file**
```bash
java -jar target/swing-job-1.0-SNAPSHOT.jar
```

**Note on PowerShell:** Use quotes around the parameter:
```powershell
mvn exec:java "-Dexec.mainClass=com.example.App"
```

## Database Configuration

The database connection is configured in `src/main/java/com/example/db/DBConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";
```

Update these values if your MySQL setup differs.

## User Roles

The application supports three user roles:

### Student
- Search for jobs with filters (location, job type)
- Apply to jobs by uploading resume and cover letter
- Track application status
- View all submitted applications

### Company
- Post new job openings with full details
- View all posted jobs
- Manage job postings
- View and update application statuses from candidates

### Admin
- Dashboard with administrative controls (extensible)
- Manage users and jobs
- View system statistics

## Features

### Authentication
- User registration with role selection
- Secure login with password hashing (SHA-256)
- Session management for persistent user context

### Job Management
- Post jobs with title, description, requirements, location, type, salary, and deadline
- Search and filter jobs by location and type
- View detailed job information
- Deactivate jobs when no longer accepting applications

### Application Tracking
- Submit applications with resume and cover letter
- Prevent duplicate applications for the same job
- Track application status (Pending, Reviewed, Interview, Rejected, Accepted)
- View company's incoming applications

## Architecture

The application uses a **three-tier architecture**:

### Presentation Layer (GUI)
- Swing-based user interface
- `LoginPanel`, `StudentDashboard`, `CompanyDashboard`, `AdminDashboard`
- Dialog windows for detailed views

### Business Logic Layer (Service)
- `UserService`: User authentication and management
- `JobService`: Job posting and searching
- `ApplicationService`: Application submission and tracking
- `SessionManager`: User session management

### Data Access Layer (DAO)
- `UserDAO`: User database operations
- `JobDAO`: Job database operations
- `ApplicationDAO`: Application database operations
- `DBConnection`: JDBC database connectivity

### Model Layer
- `User`: User entity with roles (STUDENT, COMPANY, ADMIN)
- `Job`: Job posting entity
- `Application`: Job application entity

## Database Schema

### Users Table
Stores all user accounts with role-based access control.

### Jobs Table
Stores job postings with company reference, details, and active status.

### Applications Table
Links students to job applications with resume path and status tracking.

## Security Notes

- Passwords are hashed using SHA-256
- SQL queries use prepared statements to prevent SQL injection
- User sessions are managed in memory
- File uploads store only the path reference

## Future Enhancements

- Email notifications for application status updates
- Resume file upload and storage
- Interview scheduling system
- User profile pages
- Job recommendations based on student profile
- Advanced admin analytics and reporting
- Database connection pooling
- User role-based permissions system

## Troubleshooting

### Database Connection Issues
- Ensure XAMPP MySQL service is running
- Check that the database `job_portal` exists
- Verify credentials in `DBConnection.java`

### Compilation Errors
- Ensure JDK 8+ is installed
- Run `mvn clean install` to update dependencies

### Runtime Issues
- Check that the MySQL JDBC driver is in the classpath
- Verify all required classes are compiled in `target/classes`

## Project Structure

```
swing-job/
├── src/
│   ├── main/
│   │   └── java/com/example/
│   │       ├── App.java                 (Entry point)
│   │       ├── db/
│   │       │   └── DBConnection.java   (Database connectivity)
│   │       ├── dao/
│   │       │   ├── UserDAO.java
│   │       │   ├── JobDAO.java
│   │       │   └── ApplicationDAO.java
│   │       ├── model/
│   │       │   ├── User.java
│   │       │   ├── Job.java
│   │       │   └── Application.java
│   │       ├── service/
│   │       │   ├── UserService.java
│   │       │   ├── JobService.java
│   │       │   └── ApplicationService.java
│   │       ├── gui/
│   │       │   ├── MainFrame.java
│   │       │   ├── LoginPanel.java
│   │       │   ├── RegistrationDialog.java
│   │       │   ├── StudentDashboard.java
│   │       │   ├── JobDetailsDialog.java
│   │       │   ├── CompanyDashboard.java
│   │       │   └── AdminDashboard.java
│   │       └── util/
│   │           ├── PasswordUtils.java
│   │           └── SessionManager.java
│   └── test/
│       └── java/com/example/
│           └── AppTest.java
├── docs/
│   ├── software_documentation.md
│   └── schema.sql
└── pom.xml
```

## License

This project is provided as-is for educational purposes.
