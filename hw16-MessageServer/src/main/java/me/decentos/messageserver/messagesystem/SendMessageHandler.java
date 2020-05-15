package me.decentos.messageserver.messagesystem;

import me.decentos.message.Message;

public interface SendMessageHandler {
    void handle(Message message, String host, int port);
}
