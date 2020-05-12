package me.decentos.dbserver.messagesystem;

import me.decentos.message.Message;

import java.util.Optional;

public interface RequestHandler {
    Optional<Message> handle(Message msg);
}
