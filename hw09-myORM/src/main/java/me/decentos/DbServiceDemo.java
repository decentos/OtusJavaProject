package me.decentos;

import me.decentos.core.dao.AccountDao;
import me.decentos.core.dao.UserDao;
import me.decentos.core.model.Account;
import me.decentos.core.model.User;
import me.decentos.core.service.DBServiceAccount;
import me.decentos.core.service.DBServiceUser;
import me.decentos.core.service.DbServiceAccountImpl;
import me.decentos.core.service.DbServiceUserImpl;
import me.decentos.h2.DataSourceH2;
import me.decentos.jdbc.dao.AccountDaoJdbc;
import me.decentos.jdbc.dao.UserDaoJdbc;
import me.decentos.jdbc.sessionmanager.SessionManagerJdbc;
import me.decentos.mapper.SqlMapper;
import me.decentos.mapper.jdbc.JdbcMapper;
import me.decentos.mapper.jdbc.JdbcMapperImpl;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;

public class DbServiceDemo {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        JdbcMapper<User> userMapper = new JdbcMapperImpl<>(User.class);
        JdbcMapper<Account> accountMapper = new JdbcMapperImpl<>(Account.class);

        new SqlMapper(dataSource).createTable("create table if not exists user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))", User.class);
        new SqlMapper(dataSource).createTable("create table if not exists account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)", Account.class);

        UserDao userDao = new UserDaoJdbc(sessionManager, userMapper);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        // создание несуществующих пользователей
        User user1 = new User();
        user1.setName("Ivan");
        user1.setAge(29);

        long user1Id = dbServiceUser.saveUser(user1);
        dbServiceUser.getUser(user1Id);

        User user2 = new User();
        user2.setName("Nikita");
        user2.setAge(12);

        long user2Id = dbServiceUser.saveUser(user2);
        dbServiceUser.getUser(user2Id);

        // изменение существующего
        User user1Upd = dbServiceUser.getUser(user1Id).get();
        user1Upd.setName("Mika");
        user1Upd.setAge(50);

        dbServiceUser.saveUser(user1Upd);
        dbServiceUser.getUser(user1Upd.getId());

        System.out.println("==================================");

        AccountDao accountDao = new AccountDaoJdbc(sessionManager, accountMapper);
        DBServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);

        // создание аккаунта
        Account account = new Account();
        account.setType("First");
        account.setRest(new BigDecimal("100.1"));

        long accNo = dbServiceAccount.saveAccount(account);

        // изменение существующего
        Account acc1 = dbServiceAccount.getAccount(accNo).get();
        acc1.setRest(new BigDecimal("252.5"));
        dbServiceAccount.saveAccount(acc1);
        dbServiceAccount.getAccount(accNo);

        // получение несуществующего
        dbServiceAccount.getAccount(new Account().getNo());
    }
}
