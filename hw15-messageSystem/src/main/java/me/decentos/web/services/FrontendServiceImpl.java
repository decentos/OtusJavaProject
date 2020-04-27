package me.decentos.web.services;

import me.decentos.core.dto.UserDTO;
import me.decentos.messagesystem.Message;
import me.decentos.messagesystem.MessageType;
import me.decentos.messagesystem.MsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class FrontendServiceImpl implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private final String backendServiceClientName;
    private final MsClient msClient;

    public FrontendServiceImpl(@Value("${backendServiceClientName}") String backendServiceClientName,
                               @Qualifier("frontendMsClient") @Lazy MsClient msClient) {
        this.backendServiceClientName = backendServiceClientName;
        this.msClient = msClient;
    }

    @Override
    public void getUserData(long userId, Consumer<UserDTO> dataConsumer) {
        Message outMsg = msClient.produceMessage(backendServiceClientName, userId, MessageType.USER_DATA);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void getAllUsersData(Consumer<List<UserDTO>> dataConsumer) {
        Message outMsg = msClient.produceMessage(backendServiceClientName, null, MessageType.USER_DATA_COLLECTION);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void createUser(UserDTO userDTO, Consumer<UserDTO> dataConsumer) {
        Message outMsg = msClient.produceMessage(backendServiceClientName, userDTO, MessageType.NEW_USER_DATA);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
        Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
        if (consumer == null) {
            logger.warn("consumer not found for:{}", sourceMessageId);
            return Optional.empty();
        }
        return Optional.of(consumer);
    }
}
