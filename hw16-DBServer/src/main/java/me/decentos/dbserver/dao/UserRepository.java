package me.decentos.dbserver.dao;

import me.decentos.dbserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
