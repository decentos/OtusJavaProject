package me.decentos.messageserver.appconfig;

import lombok.RequiredArgsConstructor;
import me.decentos.messageserver.messagesystem.MessageSystem;
import me.decentos.messageserver.messagesystem.MsClient;
import me.decentos.messageserver.messagesystem.MsClientImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "frontend")
    public MsClient frontendMsClient(MessageSystem messageSystem) {
        return new MsClientImpl(messageSystem);
    }

    @Bean
    @ConfigurationProperties(prefix = "dbserver")
    public MsClient databaseMsClient(MessageSystem messageSystem) {
        return new MsClientImpl(messageSystem);
    }
}
