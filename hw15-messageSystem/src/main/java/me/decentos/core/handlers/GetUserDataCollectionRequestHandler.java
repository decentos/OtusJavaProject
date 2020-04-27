package me.decentos.core.handlers;

import me.decentos.common.Serializers;
import me.decentos.core.dto.UserDTO;
import me.decentos.core.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetUserDataCollectionRequestHandler implements RequestHandler {
    private final UserService userService;

    public GetUserDataCollectionRequestHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        List<UserDTO> data = userService.getAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
                MessageType.USER_DATA_COLLECTION.getValue(), Serializers.serialize(data)));
    }
}
