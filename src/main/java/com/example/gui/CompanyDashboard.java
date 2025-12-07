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
 * Dashboard for company users.
 * Allows job posting, management, and viewing applications.
 */
public class CompanyDashboard extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JobService jobService;
    private ApplicationService applicationService;
    private JTabbedPane tabbedPane;
    private JComponent myJobsPanel;
    
    public CompanyDashboard(MainFrame mainFrame) {
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
        
        JLabel welcomeLabel = new JLabel("Welcome, " + SessionManager.getInstance().getCurrentUser().getCompanyName());
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
        
        // Post Job Tab
        JComponent postJobPanel = createPostJobPanel();
        tabbedPane.addTab("Post Job", postJobPanel);
        
        // My Jobs Tab
        myJobsPanel = createMyJobsPanel();
        tabbedPane.addTab("My Jobs", myJobsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JComponent createPostJobPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JLabel titleLabel = new JLabel("Post a New Job");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        int row = 1;
        
        // Job Title
        JLabel jobTitleLabel = new JLabel("Job Title:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(jobTitleLabel, gbc);
        
        JTextField jobTitleField = new JTextField(40);
        gbc.gridx = 1;
        panel.add(jobTitleField, gbc);
        row++;
        
        // Location
        JLabel locationLabel = new JLabel("Location:");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(locationLabel, gbc);
        
        JTextField locationField = new JTextField(40);
        gbc.gridx = 1;
        panel.add(locationField, gbc);
        row++;
        
        // Job Type
        JLabel jobTypeLabel = new JLabel("Job Type:");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(jobTypeLabel, gbc);
        
        String[] jobTypes = {"FULL_TIME", "PART_TIME", "INTERNSHIP", "CONTRACT"};
        JComboBox<String> jobTypeCombo = new JComboBox<>(jobTypes);
        gbc.gridx = 1;
        panel.add(jobTypeCombo, gbc);
        row++;
        
        // Salary Range
        JLabel salaryLabel = new JLabel("Salary Range:");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(salaryLabel, gbc);
        
        JTextField salaryField = new JTextField(40);
        gbc.gridx = 1;
        panel.add(salaryField, gbc);
        row++;
        
        // Deadline
        JLabel deadlineLabel = new JLabel("Deadline (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(deadlineLabel, gbc);
        
        JTextField deadlineField = new JTextField(40);
        gbc.gridx = 1;
        panel.add(deadlineField, gbc);
        row++;
        
        // Description
        JLabel descLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(descLabel, gbc);
        
        JTextArea descArea = new JTextArea(5, 40);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(400, 100));
        gbc.gridx = 1;
        gbc.weighty = 1.0;
        panel.add(descScroll, gbc);
        row++;
        
        // Requirements
        JLabel reqLabel = new JLabel("Requirements:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weighty = 0;
        panel.add(reqLabel, gbc);
        
        JTextArea reqArea = new JTextArea(5, 40);
        reqArea.setLineWrap(true);
        reqArea.setWrapStyleWord(true);
        JScrollPane reqScroll = new JScrollPane(reqArea);
        reqScroll.setPreferredSize(new Dimension(400, 100));
        gbc.gridx = 1;
        gbc.weighty = 1.0;
        panel.add(reqScroll, gbc);
        row++;
        
        // Message label
        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        panel.add(messageLabel, gbc);
        row++;
        
        // Post button
        JButton postButton = new JButton("Post Job");
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePostJob(jobTitleField, locationField, jobTypeCombo, salaryField, 
                             deadlineField, descArea, reqArea, messageLabel);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(postButton, gbc);
        
        return new JScrollPane(panel);
    }
    
    private void handlePostJob(JTextField jobTitleField, JTextField locationField, JComboBox<String> jobTypeCombo,
                              JTextField salaryField, JTextField deadlineField, JTextArea descArea, 
                              JTextArea reqArea, JLabel messageLabel) {
        String title = jobTitleField.getText().trim();
        String location = locationField.getText().trim();
        String jobType = (String) jobTypeCombo.getSelectedItem();
        String salary = salaryField.getText().trim();
        String deadline = deadlineField.getText().trim();
        String description = descArea.getText().trim();
        String requirements = reqArea.getText().trim();
        
        if (title.isEmpty() || location.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
            messageLabel.setText("Please fill in all required fields");
            return;
        }
        
        try {
            Job job = new Job();
            job.setCompanyId(SessionManager.getInstance().getCurrentUserId());
            job.setTitle(title);
            job.setLocation(location);
            job.setJobType(Job.JobType.valueOf(jobType));
            job.setSalaryRange(salary);
            job.setDeadline(java.sql.Date.valueOf(deadline));
            job.setDescription(description);
            job.setRequirements(requirements);
            
            if (jobService.postJob(job)) {
                JOptionPane.showMessageDialog(null, "Job posted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                jobTitleField.setText("");
                locationField.setText("");
                salaryField.setText("");
                deadlineField.setText("");
                descArea.setText("");
                reqArea.setText("");
                messageLabel.setText("");
                refreshMyJobs();
            } else {
                messageLabel.setText("Failed to post job");
            }
        } catch (IllegalArgumentException ex) {
            messageLabel.setText("Invalid date format. Use YYYY-MM-DD");
        }
    }
    
    private JPanel createMyJobsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane();
        JPanel jobsListPanel = new JPanel();
        jobsListPanel.setLayout(new BoxLayout(jobsListPanel, BoxLayout.Y_AXIS));
        jobsListPanel.setBackground(Color.WHITE);
        
        scrollPane.setViewportView(jobsListPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshMyJobs();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.NORTH);
        
        refreshMyJobs();
        
        return panel;
    }
    
    private void refreshMyJobs() {
        List<Job> jobs = jobService.getJobsByCompanyId(SessionManager.getInstance().getCurrentUserId());
        
        JPanel panel = (JPanel) myJobsPanel;
        JScrollPane scrollPane = (JScrollPane) panel.getComponent(1);
        JPanel jobsListPanel = (JPanel) scrollPane.getViewport().getView();
        jobsListPanel.removeAll();
        
        if (jobs.isEmpty()) {
            jobsListPanel.add(new JLabel("You haven't posted any jobs yet."));
        } else {
            for (Job job : jobs) {
                JPanel jobPanel = createJobItemPanel(job);
                jobsListPanel.add(jobPanel);
            }
        }
        
        jobsListPanel.revalidate();
        jobsListPanel.repaint();
    }
    
    private JPanel createJobItemPanel(Job job) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panel.setPreferredSize(new Dimension(0, 80));
        
        JLabel titleLabel = new JLabel(job.getTitle() + " (" + (job.isActive() ? "Active" : "Inactive") + ")");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel infoLabel = new JLabel("Location: " + job.getLocation() + " | Type: " + job.getJobType() + " | Deadline: " + job.getDeadline());
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.LIGHT_GRAY);
        infoPanel.add(titleLabel);
        infoPanel.add(infoLabel);
        
        panel.add(infoPanel, BorderLayout.WEST);
        
        JButton viewApplicationsButton = new JButton("View Applications");
        viewApplicationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openApplicationsDialog(job);
            }
        });
        panel.add(viewApplicationsButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private void openApplicationsDialog(Job job) {
        List<Application> applications = applicationService.getApplicationsByJobId(job.getJobId());
        
        JDialog dialog = new JDialog(mainFrame, "Applications for " + job.getTitle(), true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(mainFrame);
        
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        JPanel appListPanel = new JPanel();
        appListPanel.setLayout(new BoxLayout(appListPanel, BoxLayout.Y_AXIS));
        
        for (Application app : applications) {
            JPanel appPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            appPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            appPanel.add(new JLabel("Student ID: " + app.getStudentId() + " | Status: " + app.getStatus() + " | Applied: " + app.getAppliedAt()));
            
            String[] statuses = {"PENDING", "REVIEWED", "INTERVIEW", "REJECTED", "ACCEPTED"};
            JComboBox<String> statusCombo = new JComboBox<>(statuses);
            statusCombo.setSelectedItem(app.getStatus().toString());
            appPanel.add(statusCombo);
            
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applicationService.updateApplicationStatus(app.getApplicationId(), 
                        Application.ApplicationStatus.valueOf((String) statusCombo.getSelectedItem()));
                    JOptionPane.showMessageDialog(dialog, "Status updated successfully!");
                }
            });
            appPanel.add(updateButton);
            
            appListPanel.add(appPanel);
        }
        
        scrollPane.setViewportView(appListPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}
