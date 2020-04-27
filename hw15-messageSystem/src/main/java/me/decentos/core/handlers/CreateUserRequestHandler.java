package me.decentos.core.handlers;

import me.decentos.common.Serializers;
import me.decentos.core.dto.UserDTO;
import me.decentos.core.model.User;
import me.decentos.core.service.UserService;
import me.decentos.messagesystem.Message;
import me.decentos.messagesystem.MessageType;
import me.decentos.messagesystem.RequestHandler;

import java.util.Optional;

public class CreateUserRequestHandler implements RequestHandler {
    private final UserService userService;

    public CreateUserRequestHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        UserDTO data = Serializers.deserialize(msg.getPayload(), UserDTO.class);

        User newUser = new User(data.getName());
        newUser.setLogin(data.getLogin());
        newUser.setPassword(data.getPassword());
        newUser.setAdmin(data.isAdmin());

        long userId = userService.saveUser(newUser);

        Optional<User> userOptional = userService.getUser(userId);
        UserDTO result = null;
        if (userOptional.isPresent())
            result = new UserDTO(userOptional.get());

        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
                MessageType.USER_DATA.getValue(), Serializers.serialize(result)));
    }
}
