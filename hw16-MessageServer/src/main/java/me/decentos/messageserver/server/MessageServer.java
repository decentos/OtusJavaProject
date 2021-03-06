package me.decentos.messageserver.server;

import me.decentos.message.Message;
import me.decentos.messageserver.messagesystem.MessageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class MessageServer {
    private static final Logger logger = LoggerFactory.getLogger(MessageServer.class);

    @Value("${messageServer.port}")
    private int messageServerPort;

    @Autowired
    private MessageSystem messageSystem;

    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(messageServerPort)) {
            logger.info("Starting MessageServer on port: {}", messageServerPort);
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Waiting for client connection...");
                try (Socket clientSocket = serverSocket.accept()) {
                    clientHandler(clientSocket);
                }
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void clientHandler(Socket clientSocket) {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {
            Message receivedMessage = (Message) ois.readObject();
            logger.info("Received from {}: message ID[{}] ", receivedMessage.getFrom(), receivedMessage.getId());
            messageSystem.newMessage(receivedMessage);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }
}
