package com.electricity.repository;

import com.electricity.model.Bill;
import com.electricity.model.Consumer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BillRepository {

    private final JdbcTemplate jdbcTemplate;

    public BillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveConsumer(Consumer consumer) {
        String sql = "INSERT INTO consumers (consumer_id, name, address, mobile) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                consumer.getConsumerId(),
                consumer.getName(),
                consumer.getAddress(),
                consumer.getMobile());
    }

    public List<Consumer> findAllConsumers() {
        String sql = "SELECT * FROM consumers ORDER BY id DESC";
        return jdbcTemplate.query(sql, consumerRowMapper());
    }

    public Consumer findConsumerById(String consumerId) {
        String sql = "SELECT * FROM consumers WHERE consumer_id = ?";
        List<Consumer> list = jdbcTemplate.query(sql, consumerRowMapper(), consumerId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int saveBill(Bill bill) {
        String sql = "INSERT INTO bills (consumer_id, consumer_name, previous_reading, current_reading, units_consumed, energy_charge, fixed_charge, additional_charges, total_amount, bill_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                bill.getConsumerId(),
                bill.getConsumerName(),
                bill.getPreviousReading(),
                bill.getCurrentReading(),
                bill.getUnitsConsumed(),
                bill.getEnergyCharge(),
                bill.getFixedCharge(),
                bill.getAdditionalCharges(),
                bill.getTotalAmount(),
                bill.getBillDate());

        Integer id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM bills", Integer.class);
        return id == null ? 0 : id;
    }

    public Bill findBillById(int id) {
        String sql = "SELECT * FROM bills WHERE id = ?";
        List<Bill> list = jdbcTemplate.query(sql, billRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Bill> findAllBills() {
        String sql = "SELECT * FROM bills ORDER BY bill_date DESC";
        return jdbcTemplate.query(sql, billRowMapper());
    }

    private RowMapper<Consumer> consumerRowMapper() {
        return (rs, rowNum) -> {
            Consumer consumer = new Consumer();
            consumer.setId(rs.getInt("id"));
            consumer.setConsumerId(rs.getString("consumer_id"));
            consumer.setName(rs.getString("name"));
            consumer.setAddress(rs.getString("address"));
            consumer.setMobile(rs.getString("mobile"));
            return consumer;
        };
    }

    private RowMapper<Bill> billRowMapper() {
        return (rs, rowNum) -> {
            Bill bill = new Bill();
            bill.setId(rs.getInt("id"));
            bill.setConsumerId(rs.getString("consumer_id"));
            bill.setConsumerName(rs.getString("consumer_name"));
            bill.setPreviousReading(rs.getInt("previous_reading"));
            bill.setCurrentReading(rs.getInt("current_reading"));
            bill.setUnitsConsumed(rs.getInt("units_consumed"));
            bill.setEnergyCharge(rs.getBigDecimal("energy_charge"));
            bill.setFixedCharge(rs.getBigDecimal("fixed_charge"));
            bill.setAdditionalCharges(rs.getBigDecimal("additional_charges"));
            bill.setTotalAmount(rs.getBigDecimal("total_amount"));
            Timestamp timestamp = rs.getTimestamp("bill_date");
            if (timestamp != null) {
                bill.setBillDate(timestamp.toLocalDateTime());
            }
            return bill;
        };
    }
}
