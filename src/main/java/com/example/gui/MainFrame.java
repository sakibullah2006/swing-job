package com.example.gui;

import com.example.util.SessionManager;
import com.example.model.User;
import javax.swing.*;

/**
 * Main application frame that manages navigation between different UI panels.
 */
public class MainFrame extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JPanel currentPanel;
    private LoginPanel loginPanel;
    private StudentDashboard studentDashboard;
    private CompanyDashboard companyDashboard;
    private AdminDashboard adminDashboard;
    
    public MainFrame() {
        setTitle("Job Management Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Show login panel initially
        loginPanel = new LoginPanel(this);
        setContentPane(loginPanel);
        currentPanel = loginPanel;
        
        setVisible(true);
    }
    
    /**
     * Navigate to student dashboard.
     */
    public void navigateToStudentDashboard(User student) {
        if (studentDashboard == null) {
            studentDashboard = new StudentDashboard(this);
        }
        switchPanel(studentDashboard);
    }
    
    /**
     * Navigate to company dashboard.
     */
    public void navigateToCompanyDashboard(User company) {
        if (companyDashboard == null) {
            companyDashboard = new CompanyDashboard(this);
        }
        switchPanel(companyDashboard);
    }
    
    /**
     * Navigate to admin dashboard.
     */
    public void navigateToAdminDashboard(User admin) {
        if (adminDashboard == null) {
            adminDashboard = new AdminDashboard(this);
        }
        switchPanel(adminDashboard);
    }
    
    /**
     * Navigate back to login.
     */
    public void navigateToLogin() {
        SessionManager.getInstance().logout();
        loginPanel = new LoginPanel(this);
        switchPanel(loginPanel);
    }
    
    /**
     * Switch the current panel displayed in the frame.
     */
    private void switchPanel(JPanel newPanel) {
        // Remove current panel from frame
        getContentPane().removeAll();
        
        // Set new panel as content pane
        setContentPane(newPanel);
        
        // Refresh display
        getContentPane().revalidate();
        getContentPane().repaint();
        
        currentPanel = newPanel;
    }
}
