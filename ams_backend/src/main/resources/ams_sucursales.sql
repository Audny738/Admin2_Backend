-- Active: 1713415795143@@127.0.0.1@3306
-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS ams_sucursales;
USE ams_sucursales;
-- Creación de tablas sin claves foraneas
CREATE TABLE IF NOT EXISTS administrative_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS controlled_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    is_active BOOLEAN,
    name VARCHAR(255),
    salary DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS branches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS jobs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    area VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS days (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20)
);

-- Creación de tablas con claves foraneas

CREATE TABLE IF NOT EXISTS check_in_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    entry_datetime DATETIME,
    controlled_user_id INT,
    FOREIGN KEY (controlled_user_id) REFERENCES controlled_users(id)
);

CREATE TABLE IF NOT EXISTS check_out_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    exit_datetime DATETIME,
    controlled_user_id INT,
    FOREIGN KEY (controlled_user_id) REFERENCES controlled_users(id)
);

CREATE TABLE IF NOT EXISTS authentication_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    controlled_user_id INT,
    password_hash VARCHAR(255),
    FOREIGN KEY (controlled_user_id) REFERENCES controlled_users(id)
);

CREATE TABLE IF NOT EXISTS user_jobs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    controlled_user_id INT,
    job_id INT,
    FOREIGN KEY (controlled_user_id) REFERENCES controlled_users(id),
    FOREIGN KEY (job_id) REFERENCES jobs(id)
);

CREATE TABLE IF NOT EXISTS entry_time (
    id INT AUTO_INCREMENT PRIMARY KEY,
    controlled_user_id INT,
    entry_time TIME,
    day_id INT,
    FOREIGN KEY (controlled_user_id) REFERENCES controlled_users(id),
    FOREIGN KEY (day_id) REFERENCES days(id)
);

CREATE TABLE IF NOT EXISTS departure_time (
    id INT AUTO_INCREMENT PRIMARY KEY,
    controlled_user_id INT,
    exit_time TIME,
    day_id INT,
    FOREIGN KEY (controlled_user_id) REFERENCES controlled_users(id),
    FOREIGN KEY (day_id) REFERENCES days(id)
);

CREATE TABLE IF NOT EXISTS user_branches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    controlled_user_id INT,
    branch_id INT,
    FOREIGN KEY (controlled_user_id) REFERENCES controlled_users(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

USE ams_sucursales;
DELETE FROM days;
USE ams_sucursales;
INSERT INTO days (name) VALUES ('Lunes'), ('Martes'), ('Miércoles'), ('Jueves'), ('Viernes'), ('Sábado'), ('Domingo');

