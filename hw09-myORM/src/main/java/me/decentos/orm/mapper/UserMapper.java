package me.decentos.orm.mapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserMapper extends ORMapper {
    private final static Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public UserMapper(DataSource dataSource) {
        super(dataSource, "create table if not exists user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))");
    }

    @Override
    public void createTable() throws SQLException {
        super.createTable();
        logger.info("Table for User created");
    }
}
