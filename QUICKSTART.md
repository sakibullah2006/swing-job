# Quick Start Guide - Job Management Portal

## First Time Setup (5 minutes)

### Step 1: Ensure MySQL is Running
- If using XAMPP: Open XAMPP Control Panel and click "Start" next to MySQL
- If using standalone MySQL: Ensure the MySQL service is running

### Step 2: Create Database
```bash
mysql -u root < docs/schema.sql
```

### Step 3: Build the Application
```bash
mvn clean install
```

### Step 4: Run the Application

**On Windows PowerShell:**
```powershell
mvn exec:java "-Dexec.mainClass=com.example.App"
```

**On Command Prompt or Linux/macOS:**
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

The application GUI window will launch automatically.

## Testing the Application

### Test Account Credentials

**Admin Account:**
```
Username: admin
Password: admin123
```

**Company Account:**
```
Username: techcorp
Password: company123
```

**Student Account:**
```
Username: john_student
Password: student123
```

### Sample Data

To populate the database with sample data, run this SQL:

```sql
-- Create test admin (passwords are hashed with SHA-256)
INSERT INTO users (username, password_hash, email, role, first_name, last_name)
VALUES ('admin', 'T8W/LzqJvX3h5nQ2K8pR9mZ1vX5tY7bQ3cD8eF2gH4', 'admin@jobportal.com', 'ADMIN', 'Admin', 'User');

-- Create test company
INSERT INTO users (username, password_hash, email, role, company_name, phone_number)
VALUES ('techcorp', 'T8W/LzqJvX3h5nQ2K8pR9mZ1vX5tY7bQ3cD8eF2gH4', 'hr@techcorp.com', 'COMPANY', 'Tech Corporation', '123-456-7890');

-- Create test student
INSERT INTO users (username, password_hash, email, role, first_name, last_name, phone_number)
VALUES ('john_student', 'T8W/LzqJvX3h5nQ2K8pR9mZ1vX5tY7bQ3cD8eF2gH4', 'john@student.com', 'STUDENT', 'John', 'Smith', '555-1234');

-- Add sample job posting
INSERT INTO jobs (company_id, title, description, requirements, location, job_type, salary_range, deadline, is_active)
VALUES (2, 'Junior Java Developer', 'We are looking for a talented Java developer to join our team.', 
        'Java, Spring Boot, MySQL', 'San Francisco, CA', 'FULL_TIME', '$80,000 - $100,000', '2025-12-31', TRUE);

-- Add another sample job
INSERT INTO jobs (company_id, title, description, requirements, location, job_type, salary_range, deadline, is_active)
VALUES (2, 'Frontend Developer', 'Build responsive web applications with React and Vue.', 
        'HTML, CSS, JavaScript, React', 'Remote', 'FULL_TIME', '$75,000 - $95,000', '2025-12-25', TRUE);
```

## User Guide

### For Students

1. **Register/Login**
   - Click "Register" to create a new student account
   - Enter username, email, password, and personal details
   - Select "STUDENT" as your role

2. **Search for Jobs**
   - In the "Search Jobs" tab, use filters for location and job type
   - Click "Search" to find matching jobs
   - Click "View & Apply" to see full job details

3. **Apply to Jobs**
   - Provide your resume path (e.g., `/path/to/resume.pdf`)
   - Optionally add a cover letter
   - Click "Submit Application"

4. **Track Applications**
   - Go to "My Applications" tab
   - View all your submitted applications and their statuses

### For Companies

1. **Register/Login**
   - Click "Register" and select "COMPANY" role
   - Enter company details and contact information

2. **Post Jobs**
   - Go to "Post Job" tab
   - Fill in all required fields (title, location, description, deadline)
   - Select job type and add salary range
   - Click "Post Job"

3. **Manage Jobs**
   - Go to "My Jobs" tab
   - View all your posted jobs
   - Click "View Applications" to see applications from candidates

4. **Review Applications**
   - Click "View Applications" for any job
   - Change application status (Pending → Reviewed → Interview → Accepted/Rejected)
   - Click "Update" to save changes

### For Admins

1. **Access Admin Dashboard**
   - Login with admin credentials
   - View administrative controls
   - (Features can be extended as needed)

## Common Issues and Solutions

### Issue: "Failed to connect to MySQL"
**Solution:** 
- Ensure XAMPP is running
- Check that MySQL port is 3306
- Verify database `job_portal` exists

### Issue: "User not found" during login
**Solution:**
- Create a new account through the Register button
- Or use the sample data SQL provided above

### Issue: Cannot submit application
**Solution:**
- Ensure resume path is filled
- Check that you haven't already applied to this job
- Verify you are logged in as a student

## API/Service Examples

See individual service classes for more details:
- `UserService.java` - Authentication and user management
- `JobService.java` - Job posting and searching
- `ApplicationService.java` - Application submission and tracking

## Tips and Tricks

- Use filter in job search to narrow down results
- Hover over buttons to see tooltips
- The resume path can be a relative or absolute file path
- Job deadlines should be in YYYY-MM-DD format when posting

## Getting Help

- Check the main `software_documentation.md` for detailed technical information
- Review the `README.md` for architecture and project structure
- Examine DAO classes for database operation examples
- Look at Service classes for business logic implementation

## Next Steps

- Customize the application with your logo/branding
- Add email notification features
- Implement file upload for resumes
- Add more advanced filtering and search
- Create user profile management pages
