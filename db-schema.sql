-- Create the database and table for the assignment planner.
CREATE DATABASE IF NOT EXISTS assignment_planner;
USE assignment_planner;

CREATE TABLE IF NOT EXISTS assignments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    due_date DATE,
    estimate_hours INT DEFAULT 1,
    priority VARCHAR(10) DEFAULT 'Low',
    notes TEXT,
    completed TINYINT(1) DEFAULT 0,
    points INT DEFAULT 0,
    reward_redeemed TINYINT(1) DEFAULT 0,
    penalty_applied TINYINT(1) DEFAULT 0
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

ALTER TABLE assignments ADD COLUMN user_id INT;