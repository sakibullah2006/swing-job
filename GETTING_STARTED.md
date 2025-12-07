# Getting Started Checklist - Job Management Portal

## ✅ Pre-Installation Checklist

### System Requirements
- [ ] Java Development Kit (JDK) 8+ installed (`java -version`)
- [ ] Maven 3.6+ installed (`mvn --version`)
- [ ] MySQL Server running (via XAMPP or standalone)
- [ ] 1 GB+ disk space available
- [ ] 2 GB+ RAM available

## ✅ Installation Checklist

### 1. Database Setup
- [ ] XAMPP MySQL service is running (if using XAMPP)
- [ ] Or standalone MySQL service is running
- [ ] Database schema created: `mysql -u root < docs/schema.sql`
- [ ] Database `job_portal` exists: `SHOW DATABASES;`
- [ ] All tables created: `USE job_portal; SHOW TABLES;`

### 2. Project Build
- [ ] Project cloned/extracted to working directory
- [ ] Navigate to project root: `cd swing-job`
- [ ] Build project: `mvn clean install` (should see "BUILD SUCCESS")
- [ ] JAR file created: `target/swing-job-1.0-SNAPSHOT.jar`

### 3. Configuration Verification
- [ ] DBConnection.java has correct database URL
  - Default: `jdbc:mysql://localhost:3306/job_portal`
  - Update if using different host/port
- [ ] Database username correct (default: `root`)
- [ ] Database password correct (default: empty)

## ✅ Initial Verification

### Test Database Connection
```bash
mysql -u root -h localhost -e "USE job_portal; SHOW TABLES;"
```

Should show:
```
applications
jobs
users
```

### Test Build
```bash
mvn clean compile
```

Should end with: `[INFO] BUILD SUCCESS`

### Test Application Launch

**Windows PowerShell:**
```powershell
mvn exec:java "-Dexec.mainClass=com.example.App"
```

**Windows Command Prompt, Linux, or macOS:**
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

Application GUI window should appear.

## ✅ First-Time User Testing

### 1. Register Test Accounts
- [ ] Click "Register" button
- [ ] Create Student account:
  - Username: `student1`
  - Email: `student1@example.com`
  - Password: `password123`
  - Role: STUDENT
- [ ] Create Company account:
  - Username: `company1`
  - Email: `company1@example.com`
  - Password: `password123`
  - Role: COMPANY
  - Company Name: `Test Company`

### 2. Test Student Functionality
- [ ] Login with student account
- [ ] Navigate to "Search Jobs" tab
- [ ] Search jobs (should be empty initially)
- [ ] Navigate to "My Applications" tab
- [ ] Logout successfully

### 3. Test Company Functionality
- [ ] Login with company account
- [ ] Navigate to "Post Job" tab
- [ ] Fill in job details:
  - Title: `Test Java Developer`
  - Location: `New York`
  - Job Type: `FULL_TIME`
  - Deadline: `2025-12-31`
  - Description: `Test job posting`
- [ ] Click "Post Job" (should see success message)
- [ ] Navigate to "My Jobs" tab
- [ ] Posted job should appear
- [ ] Logout successfully

### 4. Test Job Application Flow
- [ ] Login with student account
- [ ] Go to "Search Jobs" tab
- [ ] Click "Search" to see all jobs
- [ ] Posted job should appear
- [ ] Click "View & Apply"
- [ ] Enter resume path: `/path/to/resume.pdf`
- [ ] Optionally add cover letter
- [ ] Click "Submit Application"
- [ ] Go to "My Applications" tab
- [ ] Application should appear with "PENDING" status
- [ ] Logout

### 5. Test Company Application Review
- [ ] Login with company account
- [ ] Go to "My Jobs" tab
- [ ] Click "View Applications"
- [ ] Student's application should appear
- [ ] Change status to "REVIEWED"
- [ ] Click "Update"
- [ ] Status should update
- [ ] Logout

## ✅ Documentation Review

Before starting development or deployment:
- [ ] Read PROJECT_SUMMARY.md for overview
- [ ] Read README.md for project structure
- [ ] Read software_documentation.md for technical details
- [ ] Read INSTALLATION.md if setup issues occur
- [ ] Read DEVELOPMENT.md before making modifications

## ✅ Troubleshooting Checklist

If you encounter issues, check:

### Database Connection Issues
- [ ] MySQL service is running
- [ ] Database `job_portal` exists
- [ ] Username/password correct in DBConnection.java
- [ ] Port is 3306 (or update accordingly)
- [ ] Firewall not blocking MySQL port

### Build Issues
- [ ] Java installed correctly (`java -version`)
- [ ] Maven installed correctly (`mvn --version`)
- [ ] All dependencies downloaded (`mvn dependency:resolve`)
- [ ] No compilation errors (`mvn compile`)

### Runtime Issues
- [ ] JAR file exists in target directory
- [ ] All required classes compiled
- [ ] No ClassNotFoundException errors
- [ ] Check console output for error messages

### UI Issues
- [ ] Buttons not responding? Click on window to focus
- [ ] Text not visible? Check color settings
- [ ] Panels not loading? Check logs for exceptions

## ✅ Common Error Solutions

| Error | Solution |
|-------|----------|
| "Connection refused" | MySQL service not running |
| "Database doesn't exist" | Run schema.sql |
| "Table not found" | Check database was created correctly |
| "mvn: command not found" | Maven not in PATH |
| "java: command not found" | Java not in PATH |
| "Cannot find main class" | Build project with `mvn compile` |

## ✅ Next Steps After Setup

### For Development
1. Read DEVELOPMENT.md
2. Understand the architecture
3. Review existing code examples
4. Make incremental changes
5. Test thoroughly

### For Production Deployment
1. Test all features thoroughly
2. Set strong MySQL password
3. Secure database access
4. Use connection pooling
5. Implement logging
6. Regular backups

### For Enhancements
1. Plan feature carefully
2. Create appropriate classes/methods
3. Write tests for new features
4. Update documentation
5. Test integration with existing code

## ✅ Performance Optimization

For optimal performance:
- [ ] Add database indexes (done in schema.sql)
- [ ] Implement connection pooling
- [ ] Cache frequently used data
- [ ] Optimize UI rendering
- [ ] Monitor memory usage

## ✅ Security Hardening

Before production:
- [ ] Set strong MySQL password
- [ ] Use prepared statements (already done)
- [ ] Input validation on all fields (mostly done)
- [ ] Secure password hashing (done with SHA-256)
- [ ] Limit database access
- [ ] Use HTTPS if exposing via web

## 🎓 Learning Checkpoints

### Beginner
- [ ] Understand project structure
- [ ] Understand three-tier architecture
- [ ] Can run the application
- [ ] Can register and login

### Intermediate
- [ ] Understand DAO pattern
- [ ] Understand Service layer
- [ ] Can add simple features
- [ ] Understand SQL basics

### Advanced
- [ ] Understand complete architecture
- [ ] Can refactor code
- [ ] Can optimize performance
- [ ] Can write production code

## 📞 Getting Help

1. **Check Documentation**
   - PROJECT_SUMMARY.md - Quick overview
   - README.md - Project structure
   - software_documentation.md - Technical details
   - INSTALLATION.md - Setup help
   - DEVELOPMENT.md - Code guide

2. **Search Code**
   - Look at similar implementations
   - Check test cases for examples
   - Review Javadoc comments

3. **Debug Issues**
   - Check console output
   - Add debug print statements
   - Use IDE debugger
   - Enable verbose Maven output: `mvn -X`

## ✨ Success Indicators

You'll know everything is working correctly when:
- ✅ Application launches without errors
- ✅ Can register new accounts for all roles
- ✅ Can login with registered accounts
- ✅ Students can search and apply for jobs
- ✅ Companies can post jobs and review applications
- ✅ Admin can access dashboard
- ✅ All features work as documented
- ✅ No SQL errors in console
- ✅ No connection errors
- ✅ Application closes cleanly

## 📊 Project Statistics

After successful setup, you should have:
- ✅ 20 Java source files
- ✅ 3 database tables
- ✅ 7 GUI panels/dialogs
- ✅ 3 DAO classes
- ✅ 3 Service classes
- ✅ Fully functional application

---

**Status:** ✅ Ready to Use  
**Last Updated:** December 8, 2025  
**Version:** 1.0-SNAPSHOT
