package me.decentos.frontend.utils;

import me.decentos.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Component
public class SocketClientMessageSystem {
    private static final Logger logger = LoggerFactory.getLogger(SocketClientMessageSystem.class);

    @Value("${messageServer.port}")
    private int messageServerPort;
    @Value("${messageServer.host}")
    private String messageServerHost;

    public boolean sendMessage(Message message) {
        try {
            try (Socket clientSocket = new Socket(messageServerHost, messageServerPort);
                 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

                oos.writeObject(message);
                logger.info("Message with ID [{}] is send to MessageServer", message.getId());
                sleep();
            }
        } catch (Exception ex) {
            logger.error("error", ex);
            return false;
        }
        return true;
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
