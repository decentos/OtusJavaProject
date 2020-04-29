package me.decentos;

import me.decentos.core.handlers.CreateUserRequestHandler;
import me.decentos.core.handlers.GetUserDataCollectionRequestHandler;
import me.decentos.core.handlers.GetUserDataRequestHandler;
import me.decentos.core.service.UserService;
import me.decentos.messagesystem.*;
import me.decentos.web.handlers.GetUserDataCollectionResponseHandler;
import me.decentos.web.handlers.GetUserDataResponseHandler;
import me.decentos.web.services.FrontendService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public MsClient backendMsClient(UserService userService,
                                    MessageSystem messageSystem,
                                    @Value("${backendServiceClientName}") String backendServiceClientName) {
        MsClient client = new MsClientImpl(backendServiceClientName, messageSystem);
        client.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(userService));
        client.addHandler(MessageType.USER_DATA_COLLECTION, new GetUserDataCollectionRequestHandler(userService));
        client.addHandler(MessageType.NEW_USER_DATA, new CreateUserRequestHandler(userService));
        messageSystem.addClient(client);

        return client;
    }

    @Bean
    public MsClient frontendMsClient(FrontendService frontendService,
                                     MessageSystem messageSystem,
                                     @Value("${frontendServiceClientName}") String frontendServiceClientName) {
        MsClient client = new MsClientImpl(frontendServiceClientName, messageSystem);
        client.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
        client.addHandler(MessageType.USER_DATA_COLLECTION, new GetUserDataCollectionResponseHandler(frontendService));
        messageSystem.addClient(client);

        return client;
    }
}
