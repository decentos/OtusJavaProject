package me.decentos.core.handlers;

import me.decentos.common.Serializers;
import me.decentos.core.dto.UserDTO;
import me.decentos.core.model.User;
import me.decentos.core.service.UserService;

import java.util.Optional;

public class GetUserDataRequestHandler implements RequestHandler {
    private final UserService userService;

    public GetUserDataRequestHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        long id = Serializers.deserialize(msg.getPayload(), Long.class);
        Optional<User> userOptional = userService.getUser(id);
        UserDTO data = null;
        if (userOptional.isPresent())
            data = new UserDTO(userOptional.get());
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
                MessageType.USER_DATA.getValue(), Serializers.serialize(data)));
    }
}
