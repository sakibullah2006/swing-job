package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.PasswordUtils;

/**
 * Service class for user-related operations.
 * Handles authentication, registration, and user management.
 */
public class UserService {
    
    private UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Register a new user.
     * 
     * @param user the user to register
     * @return true if registration is successful
     */
    public boolean registerUser(User user) {
        // Check if username already exists
        if (userDAO.usernameExists(user.getUsername())) {
            System.err.println("Username already exists: " + user.getUsername());
            return false;
        }
        
        // Check if email already exists
        if (userDAO.emailExists(user.getEmail())) {
            System.err.println("Email already registered: " + user.getEmail());
            return false;
        }
        
        // Hash the password
        String hashedPassword = PasswordUtils.hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        
        // Save user to database
        return userDAO.createUser(user);
    }
    
    /**
     * Authenticate a user by username and password.
     * 
     * @param username the username
     * @param password the plain text password
     * @return the User object if authentication is successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        
        if (user != null && PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        
        return null;
    }
    
    /**
     * Get a user by ID.
     * 
     * @param userId the user ID
     * @return the User object
     */
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
    
    /**
     * Get a user by username.
     * 
     * @param username the username
     * @return the User object
     */
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
    
    /**
     * Update user information.
     * 
     * @param user the user to update
     * @return true if update is successful
     */
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
}
