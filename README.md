USE electricity_bill_db;

INSERT INTO consumers (consumer_id, name, address, mobile_number, category) VALUES
('CUST-1001', 'Ayesha Khan', '12 Green Park, Mumbai', '9876543210', 'Domestic'),
('CUST-1002', 'Rahul Sharma', '45 Lotus Street, Pune', '9123456780', 'Commercial'),
('CUST-1003', 'Vinod Mehta', '78 River Road, Delhi', '9988776655', 'Industrial');

INSERT INTO bills (bill_number, consumer_id, consumer_name, billing_date, previous_reading, current_reading, units_consumed, energy_charge, fixed_charge, additional_charges, total_amount, tariff_summary)
VALUES
('EB1710000001', 'CUST-1001', 'Ayesha Khan', NOW(), 1200, 1365, 165, 880.00, 150.00, 65.00, 1095.00, '0-50 Units (50 units @ 3.50 = ₹175.00; 51-150 Units (100 units @ 4.50 = ₹450.00; 151-300 Units (15 units @ 6.00 = ₹90.00; '),
('EB1710000002', 'CUST-1002', 'Rahul Sharma', NOW(), 2450, 2672, 222, 1100.00, 150.00, 65.00, 1315.00, '0-50 Units (50 units @ 3.50 = ₹175.00; 51-150 Units (100 units @ 4.50 = ₹450.00; 151-300 Units (72 units @ 6.00 = ₹432.00; '),
('EB1710000003', 'CUST-1003', 'Vinod Mehta', NOW(), 8700, 8990, 290, 1350.00, 150.00, 65.00, 1565.00, '0-50 Units (50 units @ 3.50 = ₹175.00; 51-150 Units (100 units @ 4.50 = ₹450.00; 151-300 Units (140 units @ 6.00 = ₹840.00; ');
