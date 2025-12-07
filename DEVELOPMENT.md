# Development Guide - Job Management Portal

## Project Structure Overview

```
swing-job/
├── src/
│   ├── main/java/com/example/
│   │   ├── App.java                    # Entry point
│   │   ├── db/
│   │   │   └── DBConnection.java       # JDBC connection management
│   │   ├── dao/                        # Data Access Objects
│   │   │   ├── UserDAO.java
│   │   │   ├── JobDAO.java
│   │   │   └── ApplicationDAO.java
│   │   ├── model/                      # Entity classes
│   │   │   ├── User.java
│   │   │   ├── Job.java
│   │   │   └── Application.java
│   │   ├── service/                    # Business logic
│   │   │   ├── UserService.java
│   │   │   ├── JobService.java
│   │   │   └── ApplicationService.java
│   │   ├── gui/                        # Swing UI components
│   │   │   ├── MainFrame.java
│   │   │   ├── LoginPanel.java
│   │   │   ├── RegistrationDialog.java
│   │   │   ├── StudentDashboard.java
│   │   │   ├── JobDetailsDialog.java
│   │   │   ├── CompanyDashboard.java
│   │   │   └── AdminDashboard.java
│   │   └── util/                       # Utility classes
│   │       ├── PasswordUtils.java
│   │       └── SessionManager.java
│   └── test/java/com/example/
│       └── AppTest.java
├── docs/
│   ├── software_documentation.md
│   └── schema.sql
├── pom.xml
├── README.md
├── QUICKSTART.md
├── INSTALLATION.md
└── DEVELOPMENT.md
```

## Architecture Patterns

### 1. MVC-like Architecture
- **Model:** `model/` classes (User, Job, Application)
- **View:** `gui/` classes (UI panels and dialogs)
- **Controller:** `service/` classes (business logic)
- **Data:** `dao/` classes (database operations)

### 2. DAO Pattern
Each entity has a corresponding DAO class for database operations:
```java
// Example: UserDAO
UserDAO dao = new UserDAO();
User user = dao.getUserById(1);
```

### 3. Service Pattern
Service classes handle business logic and validation:
```java
// Example: UserService
UserService service = new UserService();
User user = service.authenticateUser(username, password);
```

### 4. Singleton Pattern
SessionManager uses singleton for global user context:
```java
SessionManager.getInstance().setCurrentUser(user);
User currentUser = SessionManager.getInstance().getCurrentUser();
```

## Adding New Features

### Adding a New Entity

1. **Create Model Class** (`src/main/java/com/example/model/NewEntity.java`)
```java
public class NewEntity {
    private int id;
    private String name;
    // Add getters/setters
}
```

2. **Create DAO Class** (`src/main/java/com/example/dao/NewEntityDAO.java`)
```java
public class NewEntityDAO {
    public boolean create(NewEntity entity) {
        // Database operation
    }
    
    public NewEntity getById(int id) {
        // Retrieve from database
    }
    // Add other CRUD operations
}
```

3. **Create Service Class** (`src/main/java/com/example/service/NewEntityService.java`)
```java
public class NewEntityService {
    private NewEntityDAO dao;
    
    public NewEntityService() {
        this.dao = new NewEntityDAO();
    }
    
    public boolean addEntity(NewEntity entity) {
        // Validation logic
        return dao.create(entity);
    }
}
```

4. **Update Database Schema** (`docs/schema.sql`)
```sql
CREATE TABLE IF NOT EXISTS new_entities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    -- other columns
);
```

### Adding a New UI Panel

1. **Create Panel Class** (`src/main/java/com/example/gui/NewPanel.java`)
```java
public class NewPanel extends JPanel {
    private MainFrame mainFrame;
    
    public NewPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        // Add components
    }
}
```

2. **Register in MainFrame** (`src/main/java/com/example/gui/MainFrame.java`)
```java
public void navigateToNewPanel() {
    NewPanel panel = new NewPanel(this);
    switchPanel(panel);
}
```

## Database Operations

### Example: Adding a New Database Query

1. **In DAO Class:**
```java
public List<User> searchByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email LIKE ?";
    List<User> users = new ArrayList<>();
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, "%" + email + "%");
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            users.add(mapResultSetToUser(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}
```

2. **Expose in Service Class:**
```java
public List<User> searchUsers(String email) {
    return userDAO.searchByEmail(email);
}
```

## Security Best Practices

### 1. Password Handling
- Use PasswordUtils for hashing
- Never store plain text passwords
```java
String hashedPassword = PasswordUtils.hashPassword(plainPassword);
user.setPasswordHash(hashedPassword);
```

### 2. SQL Injection Prevention
- Always use PreparedStatement
```java
// GOOD
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
pstmt.setInt(1, userId);

// BAD - Don't do this!
String query = "SELECT * FROM users WHERE id = " + userId;
```

### 3. Input Validation
- Validate user input before processing
```java
if (username.isEmpty() || username.length() < 3) {
    throw new IllegalArgumentException("Invalid username");
}
```

## Testing

### Running Tests
```bash
mvn test
```

### Writing Tests
```java
@Test
public void testFeature() {
    // Setup
    Service service = new Service();
    
    // Execute
    boolean result = service.someMethod();
    
    // Assert
    assertTrue("Expected true", result);
}
```

## Performance Optimization

### 1. Database Connection Pooling
Consider adding HikariCP for connection pooling:
```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>5.0.1</version>
</dependency>
```

### 2. Caching
Implement caching for frequently accessed data:
```java
Map<Integer, User> userCache = new HashMap<>();
```

### 3. Lazy Loading
Load UI components only when needed

### 4. Database Indexing
Ensure important columns are indexed (see schema.sql)

## Code Style Guidelines

### Naming Conventions
```java
// Classes: PascalCase
public class UserService { }

// Methods: camelCase
public void getUserById() { }

// Constants: UPPER_SNAKE_CASE
private static final String DB_URL = "...";

// Variables: camelCase
private String userName;
```

### Documentation
```java
/**
 * Retrieve a user by their username.
 * 
 * @param username the username to search for
 * @return the User object if found, null otherwise
 */
public User getUserByUsername(String username) {
    // Implementation
}
```

## Building and Deploying

### Create Executable JAR
```bash
mvn clean package
```

### Run JAR File
```bash
java -jar target/swing-job-1.0-SNAPSHOT.jar
```

### Create Standalone Application
Use tools like:
- **jpackage** (Java 16+) - Creates native installers
- **Launch4j** - Windows executable wrapper
- **AppBundler** - macOS application bundle

## Debugging

### Enable Debug Logging
```java
java -Ddebug=true -jar swing-job-1.0-SNAPSHOT.jar
```

### Debug in IDE
1. Set breakpoints in code
2. Run in debug mode
3. Step through code execution

### Common Issues and Solutions

| Issue | Solution |
|-------|----------|
| Null Pointer Exception | Check object initialization and null safety |
| SQL Exception | Verify SQL syntax and parameter binding |
| Class Not Found | Check classpath and imports |
| Database Connection Error | Verify connection details and server status |

## Future Enhancements

### Planned Features
1. Email notifications
2. Resume file upload and download
3. Interview scheduling
4. User profile pages
5. Advanced search filters
6. Job recommendations
7. Company analytics dashboard
8. Mobile app integration

### Technology Improvements
1. Migrate to Spring Framework
2. Add REST API layer
3. Implement caching layer
4. Add logging framework
5. Implement database transactions
6. Add API documentation (Swagger)
7. Containerize with Docker
8. CI/CD pipeline setup

## Contribution Guidelines

### Before Starting
1. Create a new branch: `git checkout -b feature/your-feature`
2. Keep commits focused and descriptive
3. Write tests for new features
4. Update documentation

### Code Review Checklist
- [ ] Code follows style guidelines
- [ ] Tests pass
- [ ] No SQL injection vulnerabilities
- [ ] Proper error handling
- [ ] Documentation updated
- [ ] No hardcoded values

## Resources

- [Java Documentation](https://docs.oracle.com/en/java/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [JDBC API](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)
- [Maven Documentation](https://maven.apache.org/guides/)

## Support

For development questions:
1. Check existing code examples
2. Review Javadoc comments
3. Consult online documentation
4. Test in isolation first
