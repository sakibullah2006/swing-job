package com.example.dao;

import com.example.db.DBConnection;
import com.example.model.Application;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Application operations.
 */
public class ApplicationDAO {
    
    /**
     * Create a new job application.
     */
    public boolean createApplication(Application application) {
        String sql = "INSERT INTO applications (job_id, student_id, resume_path, cover_letter, status) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, application.getJobId());
            pstmt.setInt(2, application.getStudentId());
            pstmt.setString(3, application.getResumePath());
            pstmt.setString(4, application.getCoverLetter());
            pstmt.setString(5, application.getStatus().name());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get application by ID.
     */
    public Application getApplicationById(int applicationId) {
        String sql = "SELECT * FROM applications WHERE application_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, applicationId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToApplication(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get all applications by student ID.
     */
    public List<Application> getApplicationsByStudentId(int studentId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE student_id = ? ORDER BY applied_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }
    
    /**
     * Get all applications for a specific job.
     */
    public List<Application> getApplicationsByJobId(int jobId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE job_id = ? ORDER BY applied_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jobId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }
    
    /**
     * Check if student has already applied for a job.
     */
    public boolean hasApplied(int jobId, int studentId) {
        String sql = "SELECT COUNT(*) FROM applications WHERE job_id = ? AND student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jobId);
            pstmt.setInt(2, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Update application status.
     */
    public boolean updateApplicationStatus(int applicationId, Application.ApplicationStatus status) {
        String sql = "UPDATE applications SET status = ?, reviewed_at = NOW() WHERE application_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status.name());
            pstmt.setInt(2, applicationId);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update full application details.
     */
    public boolean updateApplication(Application application) {
        String sql = "UPDATE applications SET resume_path = ?, cover_letter = ?, status = ? WHERE application_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, application.getResumePath());
            pstmt.setString(2, application.getCoverLetter());
            pstmt.setString(3, application.getStatus().name());
            pstmt.setInt(4, application.getApplicationId());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Helper method to map ResultSet to Application object.
     */
    private Application mapResultSetToApplication(ResultSet rs) throws SQLException {
        Application application = new Application();
        application.setApplicationId(rs.getInt("application_id"));
        application.setJobId(rs.getInt("job_id"));
        application.setStudentId(rs.getInt("student_id"));
        application.setResumePath(rs.getString("resume_path"));
        application.setCoverLetter(rs.getString("cover_letter"));
        application.setStatus(Application.ApplicationStatus.valueOf(rs.getString("status")));
        application.setAppliedAt(rs.getTimestamp("applied_at"));
        application.setReviewedAt(rs.getTimestamp("reviewed_at"));
        application.setUpdatedAt(rs.getTimestamp("updated_at"));
        return application;
    }
}
