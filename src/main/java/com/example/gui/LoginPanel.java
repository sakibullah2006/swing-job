package com.example.gui;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login panel for user authentication.
 */
public class LoginPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private UserService userService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    
    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userService = new UserService();
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Job Management Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Login to your account");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        add(subtitleLabel, gbc);
        
        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);
        
        // Message label
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(messageLabel, gbc);
        
        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(loginButton, gbc);
        
        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 30));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationDialog();
            }
        });
        gbc.gridx = 1;
        add(registerButton, gbc);
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password");
            return;
        }
        
        User user = userService.authenticateUser(username, password);
        
        if (user != null) {
            SessionManager.getInstance().setCurrentUser(user);
            
            // Navigate based on user role
            switch (user.getRole()) {
                case STUDENT:
                    mainFrame.navigateToStudentDashboard(user);
                    break;
                case COMPANY:
                    mainFrame.navigateToCompanyDashboard(user);
                    break;
                case ADMIN:
                    mainFrame.navigateToAdminDashboard(user);
                    break;
            }
        } else {
            messageLabel.setText("Invalid username or password");
            passwordField.setText("");
        }
    }
    
    private void openRegistrationDialog() {
        RegistrationDialog dialog = new RegistrationDialog(mainFrame, userService);
        dialog.setVisible(true);
    }
}
