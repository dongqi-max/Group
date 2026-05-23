<<<<<<< HEAD
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
=======
CREATE DATABASE IF NOT EXISTS assignment_planner;
USE assignment_planner;

DROP TABLE IF EXISTS assignments;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

<<<<<<< HEAD
ALTER TABLE assignments ADD COLUMN user_id INT;
=======
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
    penalty_applied BOOLEAN DEFAULT FALSE,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
