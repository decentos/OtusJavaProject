package me.decentos.dbserver.config;

import lombok.RequiredArgsConstructor;
import me.decentos.dbserver.messagesystem.DBServerMsClientImpl;
import me.decentos.dbserver.messagesystem.MessageType;
import me.decentos.dbserver.messagesystem.MsClient;
import me.decentos.dbserver.messagesystem.handlers.GetUserDataRequestHandler;
import me.decentos.dbserver.messagesystem.handlers.GetUsersListRequestHandler;
import me.decentos.dbserver.services.DBServiceCachedUser;
import me.decentos.dbserver.utils.SocketClientDBServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Autowired
    private DBServiceCachedUser serviceCachedUser;

    @Value("${databaseServiceClientName}")
    private String dbServerMsClientName;

    @Value("${messageServer.port}")
    private int messageServerPort;
    @Value("${messageServer.host}")
    private String messageServerHost;

    @Bean
    public SocketClientDBServer socketClientDBServer() {
        return new SocketClientDBServer();
    }

    @Bean
    public MsClient dbServerMsClientImpl() {
        var dbServerMsClient = new DBServerMsClientImpl(dbServerMsClientName, socketClientDBServer());
        dbServerMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(serviceCachedUser));
        dbServerMsClient.addHandler(MessageType.USERS_LIST, new GetUsersListRequestHandler(serviceCachedUser));
        return dbServerMsClient;
    }
}
