package me.decentos;

import me.decentos.core.dao.UserDao;
import me.decentos.core.model.AddressDataSet;
import me.decentos.core.model.PhoneDataSet;
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

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user = new User("Тестовый");
        AddressDataSet address = new AddressDataSet("Тестовая улица");
        PhoneDataSet phone1 = new PhoneDataSet("+7-999-555-33-11");
        PhoneDataSet phone2 = new PhoneDataSet("+7-666-000-77-88");

        user.setAddress(address);
        user.addPhone(phone1);
        user.addPhone(phone2);

        long id = dbServiceUser.saveUser(user);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        user = new User("Новый тест");
        address = new AddressDataSet("Новая улица");
        phone1 = new PhoneDataSet("+7-555-333-00-00");
        phone2 = new PhoneDataSet("+7-444-999-11-11");

        user.setAddress(address);
        user.addPhone(phone1);
        user.addPhone(phone2);

        id = dbServiceUser.saveUser(user);
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
