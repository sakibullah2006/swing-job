package com.example.util;

import com.example.model.User;

/**
 * Session manager for maintaining the current logged-in user.
 * Uses singleton pattern.
 */
public class SessionManager {
    
    private static SessionManager instance;
    private User currentUser;
    
    private SessionManager() {}
    
    /**
     * Get the singleton instance of SessionManager.
     * 
     * @return the SessionManager instance
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Set the current logged-in user.
     * 
     * @param user the user to set as current
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    /**
     * Get the current logged-in user.
     * 
     * @return the current user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Get the current user's ID.
     * 
     * @return the current user's ID, or -1 if no user is logged in
     */
    public int getCurrentUserId() {
        return (currentUser != null) ? currentUser.getUserId() : -1;
    }
    
    /**
     * Get the current user's role.
     * 
     * @return the current user's role, or null if no user is logged in
     */
    public User.UserRole getCurrentUserRole() {
        return (currentUser != null) ? currentUser.getRole() : null;
    }
    
    /**
     * Check if a user is logged in.
     * 
     * @return true if a user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Log out the current user.
     */
    public void logout() {
        currentUser = null;
    }
}
