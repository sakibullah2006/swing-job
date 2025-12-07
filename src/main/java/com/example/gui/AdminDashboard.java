package com.example.gui;

import com.example.util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard for admin users.
 * Provides administrative controls for managing users and jobs.
 */
public class AdminDashboard extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    
    public AdminDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Top panel with user info and logout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(51, 102, 153));
        topPanel.setPreferredSize(new Dimension(0, 50));
        
        JLabel welcomeLabel = new JLabel("Admin Dashboard - Welcome, " + SessionManager.getInstance().getCurrentUser().getUsername());
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.navigateToLogin();
            }
        });
        topPanel.add(logoutButton, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Main content area
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        
        JLabel titleLabel = new JLabel("Administrative Controls");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);
        
        // Manage Users Button
        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.setPreferredSize(new Dimension(200, 50));
        manageUsersButton.setFont(new Font("Arial", Font.PLAIN, 14));
        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AdminDashboard.this, 
                    "User management features will be implemented.", 
                    "Feature Not Implemented", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.2;
        contentPanel.add(manageUsersButton, gbc);
        
        // Manage Jobs Button
        JButton manageJobsButton = new JButton("Manage Jobs");
        manageJobsButton.setPreferredSize(new Dimension(200, 50));
        manageJobsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        manageJobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AdminDashboard.this, 
                    "Job management features will be implemented.", 
                    "Feature Not Implemented", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(manageJobsButton, gbc);
        
        // View Statistics Button
        JButton statisticsButton = new JButton("View Statistics");
        statisticsButton.setPreferredSize(new Dimension(200, 50));
        statisticsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AdminDashboard.this, 
                    "Statistics view will be implemented.", 
                    "Feature Not Implemented", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(statisticsButton, gbc);
        
        // System Settings Button
        JButton settingsButton = new JButton("System Settings");
        settingsButton.setPreferredSize(new Dimension(200, 50));
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AdminDashboard.this, 
                    "System settings features will be implemented.", 
                    "Feature Not Implemented", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(settingsButton, gbc);
        
        // Empty space
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        contentPanel.add(new JLabel(), gbc);
        
        add(contentPanel, BorderLayout.CENTER);
    }
}
