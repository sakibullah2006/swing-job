package com.example.gui;

import com.example.model.User;
import com.example.service.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Registration dialog for new user registration.
 */
public class RegistrationDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    private UserService userService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField companyNameField;
    private JComboBox<String> roleComboBox;
    private JLabel messageLabel;
    
    public RegistrationDialog(Frame owner, UserService userService) {
        super(owner, "User Registration", true);
        this.userService = userService;
        initializeUI();
        setSize(400, 500);
        setLocationRelativeTo(owner);
    }
    
    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        int row = 1;
        
        // Role selection
        JLabel roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(roleLabel, gbc);
        
        String[] roles = {"STUDENT", "COMPANY", "ADMIN"};
        roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        add(roleComboBox, gbc);
        row++;
        
        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);
        row++;
        
        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(emailLabel, gbc);
        
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);
        row++;
        
        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);
        row++;
        
        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(confirmPasswordLabel, gbc);
        
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);
        row++;
        
        // First Name
        JLabel firstNameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(firstNameLabel, gbc);
        
        firstNameField = new JTextField(20);
        gbc.gridx = 1;
        add(firstNameField, gbc);
        row++;
        
        // Last Name
        JLabel lastNameLabel = new JLabel("Last Name:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lastNameLabel, gbc);
        
        lastNameField = new JTextField(20);
        gbc.gridx = 1;
        add(lastNameField, gbc);
        row++;
        
        // Company Name (only for COMPANY role)
        JLabel companyNameLabel = new JLabel("Company Name:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(companyNameLabel, gbc);
        
        companyNameField = new JTextField(20);
        gbc.gridx = 1;
        add(companyNameField, gbc);
        row++;
        
        // Message label
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        add(messageLabel, gbc);
        row++;
        
        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(registerButton, gbc);
        
        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        gbc.gridx = 1;
        add(cancelButton, gbc);
    }
    
    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String companyName = companyNameField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();
        
        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all required fields");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match");
            return;
        }
        
        if (password.length() < 6) {
            messageLabel.setText("Password must be at least 6 characters");
            return;
        }
        
        // Create user object
        User user = new User(username, password, email, User.UserRole.valueOf(role));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCompanyName(companyName);
        
        // Register user
        if (userService.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            messageLabel.setText("Registration failed. Username or email may already exist.");
        }
    }
}
