package com.example.dao;

import com.example.db.DBConnection;
import com.example.model.Job;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Job operations.
 */
public class JobDAO {
    
    /**
     * Create a new job posting.
     */
    public boolean createJob(Job job) {
        String sql = "INSERT INTO jobs (company_id, title, description, requirements, location, job_type, salary_range, deadline) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, job.getCompanyId());
            pstmt.setString(2, job.getTitle());
            pstmt.setString(3, job.getDescription());
            pstmt.setString(4, job.getRequirements());
            pstmt.setString(5, job.getLocation());
            pstmt.setString(6, job.getJobType().name());
            pstmt.setString(7, job.getSalaryRange());
            pstmt.setDate(8, new java.sql.Date(job.getDeadline().getTime()));
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get job by ID.
     */
    public Job getJobById(int jobId) {
        String sql = "SELECT * FROM jobs WHERE job_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jobId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToJob(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get all jobs by company ID.
     */
    public List<Job> getJobsByCompanyId(int companyId) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE company_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, companyId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
    
    /**
     * Get all active jobs with optional filters.
     */
    public List<Job> searchJobs(String location, String jobType, boolean activeOnly) {
        List<Job> jobs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM jobs WHERE 1=1");
        
        if (activeOnly) {
            sql.append(" AND is_active = TRUE");
        }
        
        if (location != null && !location.isEmpty()) {
            sql.append(" AND location LIKE ?");
        }
        
        if (jobType != null && !jobType.isEmpty()) {
            sql.append(" AND job_type = ?");
        }
        
        sql.append(" ORDER BY created_at DESC");
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (location != null && !location.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + location + "%");
            }
            if (jobType != null && !jobType.isEmpty()) {
                pstmt.setString(paramIndex++, jobType);
            }
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                jobs.add(mapResultSetToJob(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
    
    /**
     * Get all active jobs.
     */
    public List<Job> getAllActiveJobs() {
        return searchJobs(null, null, true);
    }
    
    /**
     * Update job information.
     */
    public boolean updateJob(Job job) {
        String sql = "UPDATE jobs SET title = ?, description = ?, requirements = ?, location = ?, " +
                     "job_type = ?, salary_range = ?, deadline = ? WHERE job_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, job.getTitle());
            pstmt.setString(2, job.getDescription());
            pstmt.setString(3, job.getRequirements());
            pstmt.setString(4, job.getLocation());
            pstmt.setString(5, job.getJobType().name());
            pstmt.setString(6, job.getSalaryRange());
            pstmt.setDate(7, new java.sql.Date(job.getDeadline().getTime()));
            pstmt.setInt(8, job.getJobId());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deactivate a job (set is_active to false).
     */
    public boolean deactivateJob(int jobId) {
        String sql = "UPDATE jobs SET is_active = FALSE WHERE job_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jobId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Helper method to map ResultSet to Job object.
     */
    private Job mapResultSetToJob(ResultSet rs) throws SQLException {
        Job job = new Job();
        job.setJobId(rs.getInt("job_id"));
        job.setCompanyId(rs.getInt("company_id"));
        job.setTitle(rs.getString("title"));
        job.setDescription(rs.getString("description"));
        job.setRequirements(rs.getString("requirements"));
        job.setLocation(rs.getString("location"));
        job.setJobType(Job.JobType.valueOf(rs.getString("job_type")));
        job.setSalaryRange(rs.getString("salary_range"));
        job.setDeadline(rs.getDate("deadline"));
        job.setActive(rs.getBoolean("is_active"));
        job.setCreatedAt(rs.getTimestamp("created_at"));
        job.setUpdatedAt(rs.getTimestamp("updated_at"));
        return job;
    }
}
