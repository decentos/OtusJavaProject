package me.decentos.messageserver.messagesystem;

import me.decentos.message.Message;

public interface MsClient {

    void addHandler(MessageType type, SendMessageHandler sendMessageHandler);

    boolean sendMessage(Message msg);

    void handle(Message msg);

    String getName();

    <T> Message produceMessage(String to, T data, MessageType msgType);

    String getHost();

    int getPort();

}
