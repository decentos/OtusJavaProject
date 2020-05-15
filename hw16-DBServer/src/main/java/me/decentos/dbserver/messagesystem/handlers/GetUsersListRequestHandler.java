package me.decentos.dbserver.messagesystem.handlers;

import com.google.gson.Gson;
import me.decentos.dbserver.entity.User;
import me.decentos.dbserver.messagesystem.MessageType;
import me.decentos.dbserver.messagesystem.RequestHandler;
import me.decentos.dbserver.messagesystem.common.Serializers;
import me.decentos.dbserver.services.DBServiceCachedUser;
import me.decentos.message.Message;

import java.util.List;
import java.util.Optional;

public class GetUsersListRequestHandler implements RequestHandler {
    private final DBServiceCachedUser dbService;
    private final Gson gson = new Gson();

    public GetUsersListRequestHandler(DBServiceCachedUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        List<User> usersList = dbService.getUsersList();
        String jsonUsersList = gson.toJson(usersList);


        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
                MessageType.USER_DATA.getValue(), Serializers.serialize(jsonUsersList)));
    }
}
