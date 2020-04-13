package me.decentos.core.dao;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(Exception ex) {
        super(ex);
    }
}
