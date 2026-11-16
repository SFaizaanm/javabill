USE electricity_bill_db;

INSERT INTO consumers (consumer_id, name, address, mobile) VALUES
('C001', 'Aisha Khan', '12 Main Road, Pune', '9876543210'),
('C002', 'Rohit Sharma', '20 Market Street, Mumbai', '9123456780'),
('C003', 'Meera Patel', '45 Park Lane, Delhi', '9988776655');

INSERT INTO bills (consumer_id, consumer_name, previous_reading, current_reading, units_consumed, energy_charge, fixed_charge, additional_charges, total_amount, bill_date)
VALUES
('C001', 'Aisha Khan', 1200, 1365, 165, 715.00, 150.00, 65.00, 930.00, NOW()),
('C002', 'Rohit Sharma', 800, 980, 180, 790.00, 150.00, 65.00, 1005.00, NOW()),
('C003', 'Meera Patel', 1500, 1720, 220, 1020.00, 150.00, 65.00, 1235.00, NOW());
