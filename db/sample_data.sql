CREATE DATABASE IF NOT EXISTS electricity_bill_db;
USE electricity_bill_db;

DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS tariff_slabs;
DROP TABLE IF EXISTS consumers;

CREATE TABLE consumers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    consumer_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    mobile_number VARCHAR(20) NOT NULL,
    category VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tariff_slabs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    slab_name VARCHAR(100) NOT NULL,
    lower_limit INT NOT NULL,
    upper_limit INT NOT NULL,
    rate DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bill_number VARCHAR(100) NOT NULL UNIQUE,
    consumer_id VARCHAR(50) NOT NULL,
    consumer_name VARCHAR(100) NOT NULL,
    billing_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    previous_reading INT NOT NULL,
    current_reading INT NOT NULL,
    units_consumed INT NOT NULL,
    energy_charge DECIMAL(12,2) NOT NULL,
    fixed_charge DECIMAL(12,2) NOT NULL,
    additional_charges DECIMAL(12,2) NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    tariff_summary TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bill_consumer FOREIGN KEY (consumer_id) REFERENCES consumers(consumer_id) ON DELETE CASCADE
);

INSERT INTO tariff_slabs (slab_name, lower_limit, upper_limit, rate) VALUES
('0-50 Units', 0, 50, 3.50),
('51-150 Units', 51, 150, 4.50),
('151-300 Units', 151, 300, 6.00),
('301+ Units', 301, 100000, 8.00);
