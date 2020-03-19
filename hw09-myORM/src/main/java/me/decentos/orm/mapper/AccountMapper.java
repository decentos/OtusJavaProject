package me.decentos.orm.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountMapper {
    private final static Logger logger = LoggerFactory.getLogger(AccountMapper.class);
    private final DataSource dataSource;

    public AccountMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable(String createTableSql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(createTableSql)) {
            pst.executeUpdate();
        }
        logger.info("Table for Account created");
    }
}
