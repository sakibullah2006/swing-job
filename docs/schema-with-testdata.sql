-- Create Job Portal Database
CREATE DATABASE IF NOT EXISTS job_portal;
USE job_portal;

-- Users table for all user accounts (Students, Companies, Admin)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    role ENUM('STUDENT', 'COMPANY', 'ADMIN') NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    company_name VARCHAR(150),
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Jobs table for job postings
CREATE TABLE IF NOT EXISTS jobs (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description LONGTEXT NOT NULL,
    requirements LONGTEXT,
    location VARCHAR(150) NOT NULL,
    job_type ENUM('FULL_TIME', 'PART_TIME', 'INTERNSHIP', 'CONTRACT') NOT NULL,
    salary_range VARCHAR(100),
    deadline DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_company_id (company_id),
    INDEX idx_job_type (job_type),
    INDEX idx_location (location),
    INDEX idx_is_active (is_active)
);

-- Applications table for job applications
CREATE TABLE IF NOT EXISTS applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    student_id INT NOT NULL,
    resume_path VARCHAR(255),
    cover_letter LONGTEXT,
    status ENUM('PENDING', 'REVIEWED', 'INTERVIEW', 'REJECTED', 'ACCEPTED') DEFAULT 'PENDING',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMP NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY unique_application (job_id, student_id),
    INDEX idx_student_id (student_id),
    INDEX idx_status (status)
);

-- Index for faster lookups
CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_user_role ON users(role);

-- Test Data
-- Password hashes are SHA-256 hashes for "password123"

-- Student user: sakibullah (password: password123)
INSERT INTO users (username, password_hash, email, role, first_name, last_name, phone_number) 
VALUES ('sakibullah', '482c811da5d5b4bc6d497ffa98491e38', 'sakib@example.com', 'STUDENT', 'Sakibullah', 'Khan', '01234567890');

-- Company user: techcorp (password: password123)
INSERT INTO users (username, password_hash, email, role, company_name, phone_number) 
VALUES ('techcorp', '482c811da5d5b4bc6d497ffa98491e38', 'hr@techcorp.com', 'COMPANY', 'Tech Corp Inc', '01987654321');

-- Company user: softwares (password: password123)
INSERT INTO users (username, password_hash, email, role, company_name, phone_number) 
VALUES ('softwares', '482c811da5d5b4bc6d497ffa98491e38', 'contact@softwares.com', 'COMPANY', 'Software Solutions Ltd', '01555555555');

-- Admin user: admin (password: password123)
INSERT INTO users (username, password_hash, email, role, first_name, last_name, phone_number) 
VALUES ('admin', '482c811da5d5b4bc6d497ffa98491e38', 'admin@jobportal.com', 'ADMIN', 'Admin', 'User', '01111111111');

-- Sample Jobs
-- Job 1: Posted by TechCorp
INSERT INTO jobs (company_id, title, description, requirements, location, job_type, salary_range, deadline, is_active) 
VALUES (
    2, 
    'Java Developer', 
    'We are looking for an experienced Java developer to join our team. You will work on enterprise applications.',
    'Java, Spring Framework, MySQL, REST APIs, Unit Testing',
    'Dhaka',
    'FULL_TIME',
    '60000-80000',
    '2025-12-31',
    TRUE
);

-- Job 2: Posted by TechCorp
INSERT INTO jobs (company_id, title, description, requirements, location, job_type, salary_range, deadline, is_active) 
VALUES (
    2, 
    'Frontend Developer', 
    'Join our team as a Frontend Developer. Build responsive web applications with React and Angular.',
    'JavaScript, React, CSS, HTML, Git',
    'Dhaka',
    'FULL_TIME',
    '50000-65000',
    '2025-12-25',
    TRUE
);

-- Job 3: Posted by Software Solutions
INSERT INTO jobs (company_id, title, description, requirements, location, job_type, salary_range, deadline, is_active) 
VALUES (
    3, 
    'Data Analyst', 
    'Analyze data and generate insights for business decisions. Work with SQL and Python.',
    'SQL, Python, Excel, Data Visualization',
    'Chittagong',
    'FULL_TIME',
    '45000-60000',
    '2026-01-15',
    TRUE
);

-- Job 4: Posted by Software Solutions - Internship
INSERT INTO jobs (company_id, title, description, requirements, location, job_type, salary_range, deadline, is_active) 
VALUES (
    3, 
    'Web Development Intern', 
    '6-month internship in web development. Learn by doing with mentorship.',
    'HTML, CSS, JavaScript, Basic PHP',
    'Dhaka',
    'INTERNSHIP',
    '15000-25000',
    '2026-02-01',
    TRUE
);

-- Sample Applications
-- Student sakibullah applying for Java Developer job
INSERT INTO applications (job_id, student_id, resume_path, cover_letter, status) 
VALUES (
    1, 
    1, 
    '/resumes/sakibullah_resume.pdf',
    'I am very interested in this Java Developer position. I have 3 years of experience in Java development.',
    'PENDING'
);

-- Student sakibullah applying for Frontend Developer job
INSERT INTO applications (job_id, student_id, resume_path, cover_letter, status) 
VALUES (
    2, 
    1, 
    '/resumes/sakibullah_resume.pdf',
    'I would love to contribute to your frontend team with my React and Angular expertise.',
    'PENDING'
);
