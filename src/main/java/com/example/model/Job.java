package com.example.model;

import java.util.Date;

/**
 * Job model class representing a job posting.
 */
public class Job {
    private int jobId;
    private int companyId;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private JobType jobType;
    private String salaryRange;
    private Date deadline;
    private boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    
    public enum JobType {
        FULL_TIME, PART_TIME, INTERNSHIP, CONTRACT
    }
    
    // Constructors
    public Job() {}
    
    public Job(int companyId, String title, String description, String location, 
               JobType jobType, Date deadline) {
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.deadline = deadline;
        this.isActive = true;
    }
    
    // Getters and Setters
    public int getJobId() {
        return jobId;
    }
    
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
    
    public int getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getRequirements() {
        return requirements;
    }
    
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public JobType getJobType() {
        return jobType;
    }
    
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
    
    public String getSalaryRange() {
        return salaryRange;
    }
    
    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }
    
    public Date getDeadline() {
        return deadline;
    }
    
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", jobType=" + jobType +
                ", deadline=" + deadline +
                '}';
    }
}
