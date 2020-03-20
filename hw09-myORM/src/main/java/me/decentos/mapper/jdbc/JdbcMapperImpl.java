package me.decentos.mapper.jdbc;

import me.decentos.jdbc.DbExecutor;
import me.decentos.jdbc.DbExecutorException;
import me.decentos.jdbc.SqlQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final DbExecutor<T> dbExecutor;
    private Connection connection;
    private final Class<T> clazz;
    private final String selectStmt;
    private final String insertStmt;
    private final String updateStmt;

    public JdbcMapperImpl(Connection connection, Class<T> clazz) {
        dbExecutor = new DbExecutor<>();
        this.connection = connection;
        this.clazz = clazz;
        this.selectStmt = SqlQueryHelper.selectStatementForClass(this.clazz);
        this.insertStmt = SqlQueryHelper.insertStatementForClass(this.clazz);
        this.updateStmt = SqlQueryHelper.updateStatementForClass(this.clazz);
    }

    @Override
    public void createOrUpdate(T objectData) {
        try {
            Object idFieldValue = SqlQueryHelper.getIdFieldValue(objectData);
            // полагаем, что работаем только с числами, где значение по умолчанию равно 0
            if (idFieldValue == null) throw new DbExecutorException("Id field can't have null value");
            if ((long) idFieldValue == 0) {
                create(objectData);
            } else {
                update(objectData);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new DbExecutorException(ex);
        }
    }

    @Override
    public void create(T objectData) throws Exception {
        List<String> params = new ArrayList<>();
        List<Field> fields = SqlQueryHelper.getNonIdFields(clazz);

        for (Field field : fields) {
            field.setAccessible(true);
            params.add(field.get(objectData).toString());
            field.setAccessible(false);
        }

        dbExecutor.insertRecord(connection, insertStmt, params, objectData);
    }

    @Override
    public void update(T objectData) throws Exception {
        List<String> params = new ArrayList<>();
        List<Field> fields = SqlQueryHelper.getNonIdFields(clazz);

        for (Field field : fields) {
            field.setAccessible(true);
            params.add(field.get(objectData).toString());
            field.setAccessible(false);
        }

        Field idField = SqlQueryHelper.getIdField(clazz);
        if (!Modifier.isPublic(idField.getModifiers())) idField.setAccessible(true);
        params.add(idField.get(objectData).toString());

        dbExecutor.insertRecord(connection, updateStmt, params, objectData);
    }

    @Override
    public Optional<T> load(long id, Class<T> clazz) {
        try {
            return dbExecutor.selectRecord(connection, selectStmt, id,
                    resultSet -> {
                        try {
                            if (resultSet.next()) {
                                T obj = clazz.getConstructor().newInstance();
                                for (Field field : clazz.getDeclaredFields()) {
                                    int modifiers = field.getModifiers();
                                    if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
                                    if (!Modifier.isPublic(modifiers)) field.setAccessible(true);
                                    field.set(obj, resultSet.getObject(field.getName()));
                                }
                                return obj;
                            }
                            return null;
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            throw new DbExecutorException(e);
                        }
                    });
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DbExecutorException(e);
        }
    }
}
