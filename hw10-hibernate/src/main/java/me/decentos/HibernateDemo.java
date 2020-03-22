package me.decentos;

import me.decentos.core.dao.UserDao;
import me.decentos.core.model.User;
import me.decentos.core.service.DBServiceUser;
import me.decentos.core.service.DbServiceUserImpl;
import me.decentos.hibernate.HibernateUtils;
import me.decentos.hibernate.dao.UserDaoHibernate;
import me.decentos.hibernate.sessionmanager.SessionManagerHibernate;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class HibernateDemo {
    private static Logger logger = LoggerFactory.getLogger(HibernateDemo.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);


        long id = dbServiceUser.saveUser(new User(0, "Вася"));
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        id = dbServiceUser.saveUser(new User(1L, "А! Нет. Это же совсем не Вася"));
        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}
