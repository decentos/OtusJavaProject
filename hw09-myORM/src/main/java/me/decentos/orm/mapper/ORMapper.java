package me.decentos.orm.mapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Мэппинг объектов на таблицы реляционной БД.*/
public abstract class ORMapper {

    private DataSource dataSource;
    private final String ddlQuery;

    public ORMapper(DataSource dataSource, String ddlQuery) {
        this.dataSource = dataSource;
        this.ddlQuery = ddlQuery;
    }

    public void createTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(ddlQuery)) {
            pst.executeUpdate();
        }
    }
}
