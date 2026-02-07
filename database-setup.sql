-- ============================================
-- Student Management System - Database Setup
-- ============================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS student_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Step 2: Use the database
USE student_db;

-- Step 3: Verify the database is created
SELECT DATABASE();

-- ============================================
-- The 'students' table will be automatically
-- created by Hibernate when you run the app
-- ============================================

-- Optional: Create a dedicated user for the application
-- (Recommended for production)
-- CREATE USER 'student_app'@'localhost' IDENTIFIED BY 'your_secure_password';
-- GRANT ALL PRIVILEGES ON student_db.* TO 'student_app'@'localhost';
-- FLUSH PRIVILEGES;

-- ============================================
-- Sample Data (Optional - for testing)
-- ============================================

-- After running the application once, you can insert sample data:
-- INSERT INTO students (name, age, gender, email, created_at, updated_at) VALUES
-- ('Alice Johnson', 22, 'FEMALE', 'alice.johnson@example.com', NOW(), NOW()),
-- ('Bob Smith', 19, 'MALE', 'bob.smith@example.com', NOW(), NOW()),
-- ('Charlie Brown', 25, 'OTHER', 'charlie.brown@example.com', NOW(), NOW()),
-- ('Diana Prince', 21, 'FEMALE', 'diana.prince@example.com', NOW(), NOW()),
-- ('Ethan Hunt', 23, 'MALE', 'ethan.hunt@example.com', NOW(), NOW());

-- ============================================
-- Useful Queries
-- ============================================

-- View all students
-- SELECT * FROM students ORDER BY created_at DESC;

-- Count total students
-- SELECT COUNT(*) as total_students FROM students;

-- Find students by gender
-- SELECT * FROM students WHERE gender = 'MALE';

-- Find students by age range
-- SELECT * FROM students WHERE age BETWEEN 20 AND 25;

-- Search students by name
-- SELECT * FROM students WHERE name LIKE '%John%';

-- Delete all students (use with caution!)
-- DELETE FROM students;

-- Drop the database (use with caution!)
-- DROP DATABASE student_db;
