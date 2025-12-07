package com.example.service;

import com.example.dao.JobDAO;
import com.example.model.Job;
import java.util.List;

/**
 * Service class for job-related operations.
 * Handles job posting, searching, and management.
 */
public class JobService {
    
    private JobDAO jobDAO;
    
    public JobService() {
        this.jobDAO = new JobDAO();
    }
    
    /**
     * Post a new job.
     * 
     * @param job the job to post
     * @return true if posting is successful
     */
    public boolean postJob(Job job) {
        // Validate required fields
        if (job.getTitle() == null || job.getTitle().isEmpty() ||
            job.getDescription() == null || job.getDescription().isEmpty() ||
            job.getLocation() == null || job.getLocation().isEmpty() ||
            job.getDeadline() == null) {
            System.err.println("Missing required job fields");
            return false;
        }
        
        return jobDAO.createJob(job);
    }
    
    /**
     * Get a job by ID.
     * 
     * @param jobId the job ID
     * @return the Job object
     */
    public Job getJobById(int jobId) {
        return jobDAO.getJobById(jobId);
    }
    
    /**
     * Get all jobs posted by a company.
     * 
     * @param companyId the company ID
     * @return list of jobs posted by the company
     */
    public List<Job> getJobsByCompanyId(int companyId) {
        return jobDAO.getJobsByCompanyId(companyId);
    }
    
    /**
     * Search jobs with optional filters.
     * 
     * @param location the location filter (optional)
     * @param jobType the job type filter (optional)
     * @return list of matching jobs
     */
    public List<Job> searchJobs(String location, String jobType) {
        return jobDAO.searchJobs(location, jobType, true);
    }
    
    /**
     * Get all active jobs.
     * 
     * @return list of all active jobs
     */
    public List<Job> getAllActiveJobs() {
        return jobDAO.getAllActiveJobs();
    }
    
    /**
     * Update a job.
     * 
     * @param job the job to update
     * @return true if update is successful
     */
    public boolean updateJob(Job job) {
        return jobDAO.updateJob(job);
    }
    
    /**
     * Deactivate a job.
     * 
     * @param jobId the job ID
     * @return true if deactivation is successful
     */
    public boolean deactivateJob(int jobId) {
        return jobDAO.deactivateJob(jobId);
    }
}
