package me.decentos.core.repository;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(Exception ex) {
        super(ex);
    }
}
