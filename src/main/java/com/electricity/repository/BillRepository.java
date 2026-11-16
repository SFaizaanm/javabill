package com.electricity.repository;

import com.electricity.model.Bill;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillRepository {

    private final DataSource dataSource;

    public BillRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Bill save(Bill bill) {
        String sql = "INSERT INTO bills (bill_number, consumer_id, consumer_name, billing_date, previous_reading, current_reading, units_consumed, energy_charge, fixed_charge, additional_charges, total_amount, tariff_summary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, bill.getBillNumber());
            statement.setString(2, bill.getConsumerId());
            statement.setString(3, bill.getConsumerName());
            statement.setTimestamp(4, Timestamp.valueOf(bill.getBillingDate()));
            statement.setInt(5, bill.getPreviousReading());
            statement.setInt(6, bill.getCurrentReading());
            statement.setInt(7, bill.getUnitsConsumed());
            statement.setBigDecimal(8, bill.getEnergyCharge());
            statement.setBigDecimal(9, bill.getFixedCharge());
            statement.setBigDecimal(10, bill.getAdditionalCharges());
            statement.setBigDecimal(11, bill.getTotalAmount());
            statement.setString(12, bill.getTariffSummary());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                bill.setId(keys.getInt(1));
            }
            return bill;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save bill: " + e.getMessage(), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills ORDER BY billing_date DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bills.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to load bills: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return bills;
    }

    public List<Bill> findByConsumerId(String consumerId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE consumer_id = ? ORDER BY billing_date DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, consumerId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bills.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to fetch consumer bills: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return bills;
    }

    public Bill findById(int id) {
        String sql = "SELECT * FROM bills WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to fetch bill by id: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return null;
    }

    public int countBills() {
        String sql = "SELECT COUNT(*) FROM bills";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to count bills: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return 0;
    }

    public BigDecimal totalUnitsConsumed() {
        String sql = "SELECT COALESCE(SUM(units_consumed), 0) FROM bills";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to aggregate units consumed: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal totalBillingAmount() {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM bills";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to aggregate billing totals: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return BigDecimal.ZERO;
    }

    private Bill mapRow(ResultSet resultSet) throws SQLException {
        Bill bill = new Bill();
        bill.setId(resultSet.getInt("id"));
        bill.setBillNumber(resultSet.getString("bill_number"));
        bill.setConsumerId(resultSet.getString("consumer_id"));
        bill.setConsumerName(resultSet.getString("consumer_name"));
        Timestamp timestamp = resultSet.getTimestamp("billing_date");
        if (timestamp != null) {
            bill.setBillingDate(timestamp.toLocalDateTime());
        }
        bill.setPreviousReading(resultSet.getInt("previous_reading"));
        bill.setCurrentReading(resultSet.getInt("current_reading"));
        bill.setUnitsConsumed(resultSet.getInt("units_consumed"));
        bill.setEnergyCharge(resultSet.getBigDecimal("energy_charge"));
        bill.setFixedCharge(resultSet.getBigDecimal("fixed_charge"));
        bill.setAdditionalCharges(resultSet.getBigDecimal("additional_charges"));
        bill.setTotalAmount(resultSet.getBigDecimal("total_amount"));
        bill.setTariffSummary(resultSet.getString("tariff_summary"));
        return bill;
    }

    private void close(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception ignored) {
            }
        }
    }
}
