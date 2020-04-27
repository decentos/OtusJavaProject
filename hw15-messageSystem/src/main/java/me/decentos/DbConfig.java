package me.decentos;

import me.decentos.core.model.AddressDataSet;
import me.decentos.core.model.PhoneDataSet;
import me.decentos.core.model.User;
import me.decentos.core.service.UserService;
import me.decentos.hibernate.HibernateUtils;
import me.decentos.web.startup.AdminUserCreator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    @Bean
    public SessionFactory sessionFactory(@Value("${hibernateCfgFile}") String configFile) {
        return HibernateUtils.buildSessionFactory(configFile,
                User.class, AddressDataSet.class, PhoneDataSet.class);
    }

    @Bean(initMethod = "createDefaultAdminUser")
    public AdminUserCreator adminUserCreator(UserService userService) {
        return new AdminUserCreator(userService);
    }
}
