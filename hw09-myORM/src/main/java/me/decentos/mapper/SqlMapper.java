package me.decentos.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlMapper {
    private final static Logger logger = LoggerFactory.getLogger(SqlMapper.class);
    private final DataSource dataSource;

    public SqlMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable(String createTableSql, Class<?> clazz) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(createTableSql)) {
            pst.executeUpdate();
        }
        logger.info("Table for {} created", clazz.getSimpleName());
    }
}
