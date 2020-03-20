package me.decentos.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

    public void insertRecord(Connection connection, String sql, List<String> params, T objectData) throws Exception {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setString(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            if (sql.contains("insert")) {
                setupGeneratedKeyInObject(objectData, pst);
            }
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

    private void setupGeneratedKeyInObject(T objectData, PreparedStatement pst) throws Exception {
        Field idField = SqlQueryHelper.getIdField(objectData.getClass());
        if (!Modifier.isPublic(idField.getModifiers())) idField.setAccessible(true);
        try (ResultSet rs = pst.getGeneratedKeys()) {
            rs.next();
            idField.set(objectData, rs.getLong(1));
        }
    }
}
