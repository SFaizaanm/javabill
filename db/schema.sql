CREATE DATABASE IF NOT EXISTS electricity_bill_db;
USE electricity_bill_db;

DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS consumers;

CREATE TABLE consumers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    consumer_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    mobile VARCHAR(20) NOT NULL
);

CREATE TABLE bills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    consumer_id VARCHAR(50) NOT NULL,
    consumer_name VARCHAR(100) NOT NULL,
    previous_reading INT NOT NULL,
    current_reading INT NOT NULL,
    units_consumed INT NOT NULL,
    energy_charge DECIMAL(10,2) NOT NULL,
    fixed_charge DECIMAL(10,2) NOT NULL,
    additional_charges DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (consumer_id) REFERENCES consumers(consumer_id)
);
