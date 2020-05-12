package me.decentos.frontend.socketserver;

import me.decentos.frontend.services.frontendservice.FrontendService;
import me.decentos.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class FrontendSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(FrontendSocketServer.class);
    private final FrontendService frontendService;
    private final int frontendSocketServerPort;

    public FrontendSocketServer(@Value("${frontendSocketServer.port}") int frontendSocketServerPort, FrontendService frontendService) {
        this.frontendSocketServerPort = frontendSocketServerPort;
        this.frontendService = frontendService;
    }

    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(frontendSocketServerPort)) {
            logger.info("Starting frontendServerSocket on port: {}", frontendSocketServerPort);

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
            logger.info("Received from {} message ID[{}]", receivedMessage.getFrom(), receivedMessage.getId());
            frontendService.sendFrontMessage(receivedMessage);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }
}
