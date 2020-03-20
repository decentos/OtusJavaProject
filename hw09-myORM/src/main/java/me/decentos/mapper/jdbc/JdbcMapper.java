package me.decentos.mapper.jdbc;

import java.util.Optional;

public interface JdbcMapper<T> {
    void create(T objectData) throws Exception;

    void update(T objectData) throws Exception;

    void createOrUpdate(T objectData);

    Optional<T> load(long id, Class<T> clazz);
}
