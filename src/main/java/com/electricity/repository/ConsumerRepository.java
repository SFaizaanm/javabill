package com.electricity.repository;

import com.electricity.model.Consumer;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ConsumerRepository {

    private final DataSource dataSource;

    public ConsumerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Consumer consumer) {
        String sql = "INSERT INTO consumers (consumer_id, name, address, mobile_number, category) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, consumer.getConsumerId());
            statement.setString(2, consumer.getName());
            statement.setString(3, consumer.getAddress());
            statement.setString(4, consumer.getMobileNumber());
            statement.setString(5, consumer.getCategory());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save consumer: " + e.getMessage(), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public List<Consumer> findAll() {
        List<Consumer> consumers = new ArrayList<>();
        String sql = "SELECT * FROM consumers ORDER BY created_at DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Consumer consumer = mapRow(resultSet);
                consumers.add(consumer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to fetch consumers: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return consumers;
    }

    public Consumer findById(int id) {
        String sql = "SELECT * FROM consumers WHERE id = ?";
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
            throw new RuntimeException("Unable to find consumer by id: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return null;
    }

    public Consumer findByConsumerId(String consumerId) {
        String sql = "SELECT * FROM consumers WHERE consumer_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, consumerId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find consumer by consumer id: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return null;
    }

    public void update(Consumer consumer) {
        String sql = "UPDATE consumers SET consumer_id = ?, name = ?, address = ?, mobile_number = ?, category = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, consumer.getConsumerId());
            statement.setString(2, consumer.getName());
            statement.setString(3, consumer.getAddress());
            statement.setString(4, consumer.getMobileNumber());
            statement.setString(5, consumer.getCategory());
            statement.setInt(6, consumer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update consumer: " + e.getMessage(), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM consumers WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete consumer: " + e.getMessage(), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public int countConsumers() {
        String sql = "SELECT COUNT(*) FROM consumers";
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
            throw new RuntimeException("Unable to count consumers: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return 0;
    }

    private Consumer mapRow(ResultSet resultSet) throws SQLException {
        Consumer consumer = new Consumer();
        consumer.setId(resultSet.getInt("id"));
        consumer.setConsumerId(resultSet.getString("consumer_id"));
        consumer.setName(resultSet.getString("name"));
        consumer.setAddress(resultSet.getString("address"));
        consumer.setMobileNumber(resultSet.getString("mobile_number"));
        consumer.setCategory(resultSet.getString("category"));
        return consumer;
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
