# Installation Guide - Job Management Portal

## System Requirements

- **Operating System:** Windows, macOS, or Linux
- **Java Development Kit (JDK):** Version 8 or higher
- **Maven:** Version 3.6 or higher
- **MySQL Server:** Version 5.7 or higher (via XAMPP or standalone)
- **RAM:** Minimum 2GB (4GB recommended)
- **Disk Space:** Minimum 1GB for installation and database

## Step-by-Step Installation

### 1. Install Java Development Kit (JDK)

#### Windows
1. Download JDK from [Oracle Java SE Downloads](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer
3. Follow the installation wizard
4. Verify installation:
   ```bash
   java -version
   javac -version
   ```

#### macOS
```bash
brew install openjdk@11
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-11-jdk
```

### 2. Install Maven

#### Windows
1. Download Maven from [Apache Maven Project](https://maven.apache.org/download.cgi)
2. Extract to a folder (e.g., `C:\apache-maven-3.9.0`)
3. Add to PATH:
   - Right-click "This PC" → Properties
   - Click "Advanced system settings"
   - Environment Variables → New
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\apache-maven-3.9.0`
4. Edit PATH and add: `%MAVEN_HOME%\bin`
5. Verify:
   ```bash
   mvn --version
   ```

#### macOS
```bash
brew install maven
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install maven
```

### 3. Install MySQL/XAMPP

#### Option A: Using XAMPP (Recommended for beginners)

**Windows & macOS:**
1. Download XAMPP from [https://www.apachefriends.org/](https://www.apachefriends.org/)
2. Run the installer
3. Select MySQL when prompted
4. Start the XAMPP Control Panel
5. Click "Start" next to MySQL

**Linux:**
```bash
sudo apt update
sudo apt install xampp
```

#### Option B: Standalone MySQL

**Windows:**
1. Download from [MySQL Community Downloads](https://dev.mysql.com/downloads/mysql/)
2. Run installer and follow prompts
3. Ensure MySQL server is running

**macOS:**
```bash
brew install mysql
brew services start mysql
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
sudo systemctl start mysql
```

### 4. Create the Database

#### Method 1: Using phpMyAdmin (XAMPP)
1. Open browser and go to `http://localhost/phpmyadmin`
2. Login (default: username "root", no password)
3. Click "SQL" tab
4. Copy and paste contents of `docs/schema.sql`
5. Click "Go"

#### Method 2: Using Command Line
```bash
# Navigate to project directory
cd swing-job

# Run the schema file
mysql -u root < docs/schema.sql

# Or with password
mysql -u root -p < docs/schema.sql
```

#### Method 3: Manual Database Creation
```bash
mysql -u root

# In MySQL shell:
CREATE DATABASE job_portal;
USE job_portal;

# Then copy contents of docs/schema.sql and execute
```

### 5. Configure Database Connection (if needed)

Edit `src/main/java/com/example/db/DBConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";  // Update if you set a password
```

### 6. Build the Project

```bash
# Navigate to project directory
cd swing-job

# Clean and install dependencies
mvn clean install

# This will:
# - Download all dependencies
# - Compile all source files
# - Run any tests
# - Package the application
```

### 7. Run the Application

#### Method 1: Using Maven
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

#### Method 2: Using Java directly (after building)
```bash
mvn clean package
java -cp target/swing-job-1.0-SNAPSHOT.jar:lib/* com.example.App
```

#### Method 3: Create executable script (Windows)
Create `run.bat`:
```batch
@echo off
cd /d "%~dp0"
mvn exec:java -Dexec.mainClass="com.example.App"
pause
```

#### Method 4: Create executable script (Linux/macOS)
Create `run.sh`:
```bash
#!/bin/bash
cd "$(dirname "$0")"
mvn exec:java -Dexec.mainClass="com.example.App"
```

Make executable: `chmod +x run.sh`

## Troubleshooting Installation

### Issue: "mvn: command not found"
**Solution:** Maven is not in PATH
- Verify Maven installation
- Add Maven bin directory to system PATH
- Restart terminal/IDE after updating PATH

### Issue: "java: command not found"
**Solution:** JDK is not properly installed
- Verify JDK installation
- Set JAVA_HOME environment variable
- Add `%JAVA_HOME%\bin` to PATH

### Issue: "Failed to connect to MySQL"
**Solution:** Database server not running
- Ensure XAMPP MySQL is started (or standalone MySQL service)
- Check MySQL is listening on port 3306
- Verify credentials in DBConnection.java

### Issue: "Access denied for user 'root'@'localhost'"
**Solution:** Wrong MySQL password
- Check your MySQL password
- Update DBConnection.java with correct password
- Or reset MySQL password if forgotten

### Issue: "Database 'job_portal' doesn't exist"
**Solution:** Database not created
- Run schema.sql: `mysql -u root < docs/schema.sql`
- Or manually create in phpMyAdmin
- Verify all tables are created: `SHOW TABLES;`

### Issue: Maven build fails with dependency errors
**Solution:** Update Maven dependencies
```bash
mvn clean
mvn dependency:resolve
mvn compile
```

### Issue: "No suitable driver found for jdbc:mysql"
**Solution:** MySQL JDBC driver not loaded
- Ensure MySQL Connector dependency in pom.xml
- Run `mvn clean dependency:resolve`
- Check target/classes for mysql-connector JAR files

## Verifying Installation

### 1. Check Java Installation
```bash
java -version
javac -version
```

### 2. Check Maven Installation
```bash
mvn --version
```

### 3. Test MySQL Connection
```bash
mysql -u root -h localhost -e "SHOW DATABASES;"
```

### 4. Build the Project
```bash
cd swing-job
mvn clean compile
```

### 5. Run the Application

**Windows PowerShell:**
```powershell
mvn exec:java "-Dexec.mainClass=com.example.App"
```

**Windows Command Prompt, Linux, or macOS:**
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

The Swing GUI application will launch in a new window.

## IDE Setup (Optional)

### Eclipse
1. Import project as "Existing Maven Projects"
2. Right-click project → Maven → Update Project
3. Right-click App.java → Run As → Java Application

### IntelliJ IDEA
1. File → Open → select pom.xml
2. Open as Project
3. Wait for Maven to sync
4. Click Run button or press Shift+F10

### VS Code
1. Install Extension Pack for Java
2. Open the project folder
3. Click "Run" link above the main method in App.java

### VS Code
1. Install Extension Pack for Java
2. Open project folder
3. Click "Run" above main method in App.java

## Next Steps

1. Read QUICKSTART.md for first-time usage
2. Check software_documentation.md for detailed information
3. Review README.md for project structure
4. Create test accounts and explore features

## Support

For issues or questions:
1. Check TROUBLESHOOTING section in this guide
2. Review error messages carefully
3. Check Maven/Java logs for detailed errors
4. Ensure all prerequisites are properly installed
5. Verify database is running and accessible
