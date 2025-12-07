package com.example;

import com.example.model.User;
import com.example.model.Job;
import com.example.model.Application;
import com.example.service.UserService;
import com.example.service.JobService;
import com.example.service.ApplicationService;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for the Job Management Portal.
 * Note: These tests assume the MySQL database is running with the schema.sql applied.
 */
public class AppTest {
    
    private UserService userService;
    private JobService jobService;
    private ApplicationService applicationService;
    
    @Before
    public void setUp() {
        userService = new UserService();
        jobService = new JobService();
        applicationService = new ApplicationService();
    }
    
    @Test
    public void testUserRegistration() {
        User user = new User();
        user.setUsername("testuser_" + System.currentTimeMillis());
        user.setPasswordHash("testpass123");
        user.setEmail("test_" + System.currentTimeMillis() + "@example.com");
        user.setRole(User.UserRole.STUDENT);
        user.setFirstName("Test");
        user.setLastName("User");
        
        boolean result = userService.registerUser(user);
        assertTrue("User registration should succeed", result);
    }
    
    @Test
    public void testUserAuthentication() {
        // Create a test user first
        String username = "authtest_" + System.currentTimeMillis();
        String password = "authpass123";
        
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(password);
        user.setEmail("auth_" + System.currentTimeMillis() + "@example.com");
        user.setRole(User.UserRole.STUDENT);
        
        userService.registerUser(user);
        
        // Try to authenticate
        User authenticatedUser = userService.authenticateUser(username, password);
        assertNotNull("Authentication should return a user", authenticatedUser);
        assertEquals("Username should match", username, authenticatedUser.getUsername());
    }
    
    @Test
    public void testInvalidLogin() {
        User result = userService.authenticateUser("nonexistentuser", "wrongpassword");
        assertNull("Invalid credentials should return null", result);
    }
    
    @Test
    public void testDuplicateUsername() {
        String uniqueUsername = "duplicate_" + System.currentTimeMillis();
        String email = "dup_" + System.currentTimeMillis() + "@example.com";
        
        User user1 = new User();
        user1.setUsername(uniqueUsername);
        user1.setPasswordHash("pass123");
        user1.setEmail(email);
        user1.setRole(User.UserRole.STUDENT);
        
        boolean result1 = userService.registerUser(user1);
        assertTrue("First registration should succeed", result1);
        
        // Try to register with same username
        User user2 = new User();
        user2.setUsername(uniqueUsername);
        user2.setPasswordHash("pass456");
        user2.setEmail("different_" + System.currentTimeMillis() + "@example.com");
        user2.setRole(User.UserRole.STUDENT);
        
        boolean result2 = userService.registerUser(user2);
        assertFalse("Duplicate username should fail", result2);
    }
    
    @Test
    public void testModelEquality() {
        User user = new User("testuser", "hash", "test@example.com", User.UserRole.STUDENT);
        user.setUserId(1);
        
        assertEquals("User ID should be 1", 1, user.getUserId());
        assertEquals("Username should match", "testuser", user.getUsername());
        assertEquals("Role should be STUDENT", User.UserRole.STUDENT, user.getRole());
    }
    
    @Test
    public void testApplicationModel() {
        Application app = new Application(1, 1, "/path/to/resume.pdf");
        
        assertEquals("Job ID should be 1", 1, app.getJobId());
        assertEquals("Student ID should be 1", 1, app.getStudentId());
        assertEquals("Resume path should match", "/path/to/resume.pdf", app.getResumePath());
        assertEquals("Status should be PENDING", Application.ApplicationStatus.PENDING, app.getStatus());
    }
    
    @Test
    public void testJobModel() {
        Job job = new Job();
        job.setTitle("Test Job");
        job.setLocation("Test Location");
        job.setJobType(Job.JobType.INTERNSHIP);
        job.setSalaryRange("$30,000 - $40,000");
        
        assertEquals("Title should match", "Test Job", job.getTitle());
        assertEquals("Location should match", "Test Location", job.getLocation());
        assertEquals("Job type should be INTERNSHIP", Job.JobType.INTERNSHIP, job.getJobType());
    }
    
    @Test
    public void testApplicationStatus() {
        Application app = new Application();
        app.setStatus(Application.ApplicationStatus.REVIEWED);
        assertEquals("Status should be REVIEWED", Application.ApplicationStatus.REVIEWED, app.getStatus());
        
        app.setStatus(Application.ApplicationStatus.INTERVIEW);
        assertEquals("Status should be INTERVIEW", Application.ApplicationStatus.INTERVIEW, app.getStatus());
        
        app.setStatus(Application.ApplicationStatus.ACCEPTED);
        assertEquals("Status should be ACCEPTED", Application.ApplicationStatus.ACCEPTED, app.getStatus());
    }
}
