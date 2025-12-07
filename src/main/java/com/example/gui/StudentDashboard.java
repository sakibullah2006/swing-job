package com.example.gui;

import com.example.model.Application;
import com.example.model.Job;
import com.example.service.ApplicationService;
import com.example.service.JobService;
import com.example.util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Dashboard for student users.
 * Allows job search, application submission, and tracking.
 */
public class StudentDashboard extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JobService jobService;
    private ApplicationService applicationService;
    private JTabbedPane tabbedPane;
    private JPanel jobSearchPanel;
    private JPanel applicationTrackerPanel;
    private JPanel jobsListPanel;
    private JScrollPane jobsScrollPane;
    
    public StudentDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.jobService = new JobService();
        this.applicationService = new ApplicationService();
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Top panel with user info and logout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(51, 102, 153));
        topPanel.setPreferredSize(new Dimension(0, 50));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + SessionManager.getInstance().getCurrentUser().getUsername());
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
        
        // Tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        
        // Job Search Tab
        jobSearchPanel = createJobSearchPanel();
        tabbedPane.addTab("Search Jobs", jobSearchPanel);
        
        // Application Tracker Tab
        applicationTrackerPanel = createApplicationTrackerPanel();
        tabbedPane.addTab("My Applications", applicationTrackerPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Load job list after UI is fully initialized
        SwingUtilities.invokeLater(() -> refreshJobList("", ""));
    }
    
    private JPanel createJobSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Search criteria panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel locationLabel = new JLabel("Location:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(locationLabel, gbc);
        
        JTextField locationField = new JTextField(20);
        gbc.gridx = 1;
        searchPanel.add(locationField, gbc);
        
        JLabel jobTypeLabel = new JLabel("Job Type:");
        gbc.gridx = 2;
        searchPanel.add(jobTypeLabel, gbc);
        
        String[] jobTypes = {"", "FULL_TIME", "PART_TIME", "INTERNSHIP", "CONTRACT"};
        JComboBox<String> jobTypeCombo = new JComboBox<>(jobTypes);
        gbc.gridx = 3;
        searchPanel.add(jobTypeCombo, gbc);
        
        JButton searchButton = new JButton("Search");
        gbc.gridx = 4;
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshJobList(locationField.getText(), (String) jobTypeCombo.getSelectedItem());
            }
        });
        searchPanel.add(searchButton, gbc);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Jobs list
        jobsScrollPane = new JScrollPane();
        jobsListPanel = new JPanel();
        jobsListPanel.setLayout(new BoxLayout(jobsListPanel, BoxLayout.Y_AXIS));
        jobsListPanel.setBackground(Color.WHITE);
        jobsScrollPane.setViewportView(jobsListPanel);
        
        panel.add(jobsScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void refreshJobList(String location, String jobType) {
        // Guard against null panel (in case called before initialization)
        if (jobsListPanel == null) {
            return;
        }
        
        List<Job> jobs;
        if (location.isEmpty() && jobType.isEmpty()) {
            jobs = jobService.getAllActiveJobs();
        } else {
            jobs = jobService.searchJobs(location.isEmpty() ? null : location, 
                                        jobType.isEmpty() ? null : jobType);
        }
        
        // Clear and repopulate job list
        jobsListPanel.removeAll();
        
        for (Job job : jobs) {
            JPanel jobPanel = createJobItemPanel(job);
            jobsListPanel.add(jobPanel);
        }
        
        jobsListPanel.revalidate();
        jobsListPanel.repaint();
    }
    
    private JPanel createJobItemPanel(Job job) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panel.setPreferredSize(new Dimension(0, 100));
        
        JLabel titleLabel = new JLabel(job.getTitle() + " at " + job.getLocation());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel typeLabel = new JLabel("Type: " + job.getJobType() + " | Deadline: " + job.getDeadline());
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.LIGHT_GRAY);
        infoPanel.add(titleLabel);
        infoPanel.add(typeLabel);
        
        panel.add(infoPanel, BorderLayout.WEST);
        
        JButton viewButton = new JButton("View & Apply");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openJobDetailsDialog(job);
            }
        });
        panel.add(viewButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private void openJobDetailsDialog(Job job) {
        JobDetailsDialog dialog = new JobDetailsDialog(mainFrame, job, applicationService);
        dialog.setVisible(true);
    }
    
    private JPanel createApplicationTrackerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane();
        JPanel applicationsPanel = new JPanel();
        applicationsPanel.setLayout(new BoxLayout(applicationsPanel, BoxLayout.Y_AXIS));
        applicationsPanel.setBackground(Color.WHITE);
        
        // Load applications
        List<Application> applications = applicationService.getApplicationsByStudentId(
            SessionManager.getInstance().getCurrentUserId());
        
        if (applications.isEmpty()) {
            applicationsPanel.add(new JLabel("You haven't applied to any jobs yet."));
        } else {
            for (Application app : applications) {
                JPanel appPanel = createApplicationItemPanel(app);
                applicationsPanel.add(appPanel);
            }
        }
        
        scrollPane.setViewportView(applicationsPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createApplicationItemPanel(Application app) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panel.setPreferredSize(new Dimension(0, 80));
        
        Job job = jobService.getJobById(app.getJobId());
        
        JLabel jobLabel = new JLabel("Job: " + (job != null ? job.getTitle() : "Unknown"));
        jobLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel statusLabel = new JLabel("Status: " + app.getStatus() + " | Applied: " + app.getAppliedAt());
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.LIGHT_GRAY);
        infoPanel.add(jobLabel);
        infoPanel.add(statusLabel);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
}
