package me.decentos.jdbc;

public class DbExecutorException extends RuntimeException {
    public DbExecutorException(Exception e) {
        super(e);
    }

    public DbExecutorException(String message) {
        super(message);
    }
}
