package me.decentos.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DbExecutor<T> {
  private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

  private Connection connection;
  private final Class<T> clazz;
  private final String selectStmt;
  private final String insertStmt;
  private final String updateStmt;

  public DbExecutor(Connection connection, Class<T> clazz) {
    this.connection = connection;
    this.clazz = clazz;
    this.selectStmt = SqlQueryHelper.selectStatementForClass(this.clazz);
    this.insertStmt = SqlQueryHelper.insertStatementForClass(this.clazz);
    this.updateStmt = SqlQueryHelper.updateStatementForClass(this.clazz);
  }

  public void create(T objectData) throws DbExecutorException {
    try {
      Savepoint savePoint = connection.setSavepoint("savePointCreate");
      try (PreparedStatement pst = connection.prepareStatement(this.insertStmt,
              Statement.RETURN_GENERATED_KEYS)) {
        List<Field> fields = SqlQueryHelper.getNonIdFields(clazz);
        for (int idx = 0; idx < fields.size(); idx++) {
          Field field = fields.get(idx);
          if (!Modifier.isPublic(field.getModifiers())) field.setAccessible(true);
          pst.setObject(idx + 1, field.get(objectData));
        }
        pst.executeUpdate();
        setupGeneratedKeyInObject(objectData, pst);
      } catch (SQLException | IllegalAccessException | NoSuchFieldException ex) {
        connection.rollback(savePoint);
        logger.error(ex.getMessage(), ex);
        throw new DbExecutorException(ex);
      }
    } catch (SQLException ex) {
      logger.error(ex.getMessage(), ex);
      throw new DbExecutorException(ex);
    }
  }

  public void update(T objectData) throws DbExecutorException {
    try {
      Savepoint savePoint = connection.setSavepoint("savePointUpdate");
      Class<T> clazz = (Class<T>) objectData.getClass();
      try (PreparedStatement pst = connection.prepareStatement(this.updateStmt,
              Statement.NO_GENERATED_KEYS)) {
        Field idField = SqlQueryHelper.getIdField(clazz);
        if (!Modifier.isPublic(idField.getModifiers())) idField.setAccessible(true);
        List<Field> nonIdfields = SqlQueryHelper.getNonIdFields(clazz);
        for (int idx = 0; idx < nonIdfields.size(); idx++) {
          Field field = nonIdfields.get(idx);
          if (!Modifier.isPublic(field.getModifiers())) field.setAccessible(true);
          pst.setObject(idx + 1, field.get(objectData));
        }
        pst.setObject(nonIdfields.size() + 1, idField.get(objectData));
        pst.executeUpdate();
      } catch (SQLException | IllegalAccessException | NoSuchFieldException ex) {
        connection.rollback(savePoint);
        logger.error(ex.getMessage(), ex);
        throw new DbExecutorException(ex);
      }
    } catch (SQLException ex) {
      logger.error(ex.getMessage(), ex);
      throw new DbExecutorException(ex);
    }
  }

  public void createOrUpdate(T objectData) throws DbExecutorException {
    try {
      Object idFieldValue = SqlQueryHelper.getIdFieldValue(objectData);
      // полагаем, что работаем только с числами, где значение по умолчанию равно 0
      if (idFieldValue==null) throw new DbExecutorException("Id field can't have null value");
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

  public Optional<T> load(long id, Class<T> clazz) throws DbExecutorException {
    try (PreparedStatement pst = connection.prepareStatement(this.selectStmt)) {
      pst.setLong(1, id);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          T obj = clazz.getConstructor().newInstance();
          // заполняем не статические поля, и не транзиентные
          for (Field field : clazz.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
            if (!Modifier.isPublic(modifiers)) field.setAccessible(true);
            field.set(obj, rs.getObject(field.getName()));
          }
          return Optional.of(obj);
        }
        return Optional.empty();
      }
    } catch (SQLException | NoSuchMethodException
            | InvocationTargetException | InstantiationException
            | IllegalAccessException e) {
      logger.error(e.getMessage(), e);
      throw new DbExecutorException(e);
    }
  }

  private void setupGeneratedKeyInObject(T objectData, PreparedStatement pst) throws NoSuchFieldException, SQLException,
          IllegalAccessException {
    Field idField = SqlQueryHelper.getIdField(clazz);
    if (!Modifier.isPublic(idField.getModifiers())) idField.setAccessible(true);
    try (ResultSet rs = pst.getGeneratedKeys()) {
      rs.next();
      idField.set(objectData, rs.getLong(1));
    }
  }
}
