package com.electricity.repository;

import com.electricity.model.TariffSlab;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TariffSlabRepository {

    private final DataSource dataSource;

    public TariffSlabRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TariffSlab> findAll() {
        List<TariffSlab> slabs = new ArrayList<>();
        String sql = "SELECT * FROM tariff_slabs ORDER BY lower_limit ASC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TariffSlab slab = new TariffSlab();
                slab.setId(resultSet.getInt("id"));
                slab.setSlabName(resultSet.getString("slab_name"));
                slab.setLowerLimit(resultSet.getInt("lower_limit"));
                slab.setUpperLimit(resultSet.getInt("upper_limit"));
                slab.setRate(resultSet.getBigDecimal("rate"));
                slabs.add(slab);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to fetch tariff slabs: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return slabs;
    }

    public int countTariffRows() {
        String sql = "SELECT COUNT(*) FROM tariff_slabs";
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
            throw new RuntimeException("Unable to count tariff entries: " + e.getMessage(), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return 0;
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
