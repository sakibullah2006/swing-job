package com.example.service;

import com.example.dao.ApplicationDAO;
import com.example.model.Application;
import java.util.List;

/**
 * Service class for application-related operations.
 * Handles job applications and tracking.
 */
public class ApplicationService {
    
    private ApplicationDAO applicationDAO;
    
    public ApplicationService() {
        this.applicationDAO = new ApplicationDAO();
    }
    
    /**
     * Submit a new job application.
     * 
     * @param application the application to submit
     * @return true if submission is successful
     */
    public boolean submitApplication(Application application) {
        // Check if student has already applied for this job
        if (applicationDAO.hasApplied(application.getJobId(), application.getStudentId())) {
            System.err.println("Student has already applied for this job");
            return false;
        }
        
        // Validate required fields
        if (application.getJobId() <= 0 || application.getStudentId() <= 0) {
            System.err.println("Invalid job ID or student ID");
            return false;
        }
        
        return applicationDAO.createApplication(application);
    }
    
    /**
     * Get an application by ID.
     * 
     * @param applicationId the application ID
     * @return the Application object
     */
    public Application getApplicationById(int applicationId) {
        return applicationDAO.getApplicationById(applicationId);
    }
    
    /**
     * Get all applications by a student.
     * 
     * @param studentId the student ID
     * @return list of applications from the student
     */
    public List<Application> getApplicationsByStudentId(int studentId) {
        return applicationDAO.getApplicationsByStudentId(studentId);
    }
    
    /**
     * Get all applications for a job.
     * 
     * @param jobId the job ID
     * @return list of applications for the job
     */
    public List<Application> getApplicationsByJobId(int jobId) {
        return applicationDAO.getApplicationsByJobId(jobId);
    }
    
    /**
     * Update application status.
     * 
     * @param applicationId the application ID
     * @param status the new status
     * @return true if update is successful
     */
    public boolean updateApplicationStatus(int applicationId, Application.ApplicationStatus status) {
        return applicationDAO.updateApplicationStatus(applicationId, status);
    }
    
    /**
     * Check if student has already applied for a job.
     * 
     * @param jobId the job ID
     * @param studentId the student ID
     * @return true if student has already applied
     */
    public boolean hasApplied(int jobId, int studentId) {
        return applicationDAO.hasApplied(jobId, studentId);
    }
}
