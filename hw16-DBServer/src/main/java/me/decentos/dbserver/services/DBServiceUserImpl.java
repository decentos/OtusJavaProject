package me.decentos.dbserver.services;

import me.decentos.dbserver.dao.UserRepository;
import me.decentos.dbserver.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceUserImpl implements DBServiceUser {

    private final UserRepository userRepository;

    public DBServiceUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsersList() {
        return userRepository.findAll();
    }
}