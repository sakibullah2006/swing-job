package com.example.gui;

import com.example.model.Application;
import com.example.model.Job;
import com.example.service.ApplicationService;
import com.example.util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for viewing job details and submitting applications.
 */
public class JobDetailsDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    private Job job;
    private ApplicationService applicationService;
    private JTextArea descriptionArea;
    private JTextField resumePathField;
    private JTextArea coverLetterArea;
    private JLabel messageLabel;
    
    public JobDetailsDialog(Frame owner, Job job, ApplicationService applicationService) {
        super(owner, job.getTitle(), true);
        this.job = job;
        this.applicationService = applicationService;
        initializeUI();
        setSize(600, 600);
        setLocationRelativeTo(owner);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        
        // Job details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Title
        JLabel titleLabel = new JLabel("Title: " + job.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        detailsPanel.add(titleLabel, gbc);
        
        // Location
        JLabel locationLabel = new JLabel("Location: " + job.getLocation());
        gbc.gridy = 1;
        detailsPanel.add(locationLabel, gbc);
        
        // Job Type
        JLabel typeLabel = new JLabel("Type: " + job.getJobType());
        gbc.gridy = 2;
        detailsPanel.add(typeLabel, gbc);
        
        // Salary
        JLabel salaryLabel = new JLabel("Salary: " + (job.getSalaryRange() != null ? job.getSalaryRange() : "Not specified"));
        gbc.gridy = 3;
        detailsPanel.add(salaryLabel, gbc);
        
        // Deadline
        JLabel deadlineLabel = new JLabel("Deadline: " + job.getDeadline());
        gbc.gridy = 4;
        detailsPanel.add(deadlineLabel, gbc);
        
        // Description
        JLabel descLabel = new JLabel("Description:");
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        detailsPanel.add(descLabel, gbc);
        
        descriptionArea = new JTextArea(5, 50);
        descriptionArea.setText(job.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(500, 100));
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        detailsPanel.add(scrollPane, gbc);
        
        add(detailsPanel, BorderLayout.NORTH);
        
        // Application form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JLabel resumeLabel = new JLabel("Resume Path:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(resumeLabel, gbc);
        
        resumePathField = new JTextField(40);
        gbc.gridx = 1;
        formPanel.add(resumePathField, gbc);
        
        JLabel coverLetterLabel = new JLabel("Cover Letter:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(coverLetterLabel, gbc);
        
        coverLetterArea = new JTextArea(5, 40);
        coverLetterArea.setLineWrap(true);
        coverLetterArea.setWrapStyleWord(true);
        JScrollPane coverLetterScroll = new JScrollPane(coverLetterArea);
        gbc.gridx = 1;
        gbc.weighty = 1.0;
        formPanel.add(coverLetterScroll, gbc);
        
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton applyButton = new JButton("Submit Application");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleApplication();
            }
        });
        buttonPanel.add(applyButton);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void handleApplication() {
        String resumePath = resumePathField.getText().trim();
        String coverLetter = coverLetterArea.getText().trim();
        
        if (resumePath.isEmpty()) {
            messageLabel.setText("Please provide a resume path");
            return;
        }
        
        int studentId = SessionManager.getInstance().getCurrentUserId();
        
        // Check if already applied
        if (applicationService.hasApplied(job.getJobId(), studentId)) {
            messageLabel.setText("You have already applied to this job");
            return;
        }
        
        // Create and submit application
        Application application = new Application(job.getJobId(), studentId, resumePath);
        application.setCoverLetter(coverLetter);
        
        if (applicationService.submitApplication(application)) {
            JOptionPane.showMessageDialog(this, "Application submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            messageLabel.setText("Failed to submit application");
        }
    }
}
