package me.decentos.mapper.jdbc;

import java.sql.Connection;
import java.util.Optional;

public interface JdbcMapper<T> {
    void create(Connection connection, T objectData) throws Exception;

    void update(Connection connection, T objectData) throws Exception;

    void createOrUpdate(Connection connection, T objectData);

    Optional<T> load(Connection connection, long id, Class<T> clazz);
}
