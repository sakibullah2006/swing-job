package com.example.model;

import java.util.Date;

/**
 * Application model class representing a job application.
 */
public class Application {
    private int applicationId;
    private int jobId;
    private int studentId;
    private String resumePath;
    private String coverLetter;
    private ApplicationStatus status;
    private Date appliedAt;
    private Date reviewedAt;
    private Date updatedAt;
    
    public enum ApplicationStatus {
        PENDING, REVIEWED, INTERVIEW, REJECTED, ACCEPTED
    }
    
    // Constructors
    public Application() {}
    
    public Application(int jobId, int studentId, String resumePath) {
        this.jobId = jobId;
        this.studentId = studentId;
        this.resumePath = resumePath;
        this.status = ApplicationStatus.PENDING;
    }
    
    // Getters and Setters
    public int getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
    
    public int getJobId() {
        return jobId;
    }
    
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getResumePath() {
        return resumePath;
    }
    
    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }
    
    public String getCoverLetter() {
        return coverLetter;
    }
    
    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
    
    public ApplicationStatus getStatus() {
        return status;
    }
    
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    public Date getAppliedAt() {
        return appliedAt;
    }
    
    public void setAppliedAt(Date appliedAt) {
        this.appliedAt = appliedAt;
    }
    
    public Date getReviewedAt() {
        return reviewedAt;
    }
    
    public void setReviewedAt(Date reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", jobId=" + jobId +
                ", studentId=" + studentId +
                ", status=" + status +
                ", appliedAt=" + appliedAt +
                '}';
    }
}
