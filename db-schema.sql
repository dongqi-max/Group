CREATE DATABASE IF NOT EXISTS assignment_planner;
USE assignment_planner;

DROP TABLE IF EXISTS assignments;

CREATE TABLE assignments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course VARCHAR(100) NOT NULL,
    title VARCHAR(200) NOT NULL,
    due_date VARCHAR(20),
    estimate_hours INT DEFAULT 1,
    priority VARCHAR(20) DEFAULT 'Low',
    notes TEXT,
    status VARCHAR(20) DEFAULT 'Not Started',
    completed BOOLEAN DEFAULT FALSE,
    points INT DEFAULT 0,
    reward_redeemed BOOLEAN DEFAULT FALSE,
    penalty_applied BOOLEAN DEFAULT FALSE
);
