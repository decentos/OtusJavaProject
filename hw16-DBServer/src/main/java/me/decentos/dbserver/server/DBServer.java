package me.decentos.dbserver.server;

import me.decentos.dbserver.messagesystem.MsClient;
import me.decentos.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class DBServer {
    private static Logger logger = LoggerFactory.getLogger(DBServer.class);

    private final MsClient dbServerMsClient;
    private final int dbServerPort;


    public DBServer(MsClient dbServerMsClient, @Value("${dbServer.port}") int dbServerPort) {
        this.dbServerMsClient = dbServerMsClient;
        this.dbServerPort = dbServerPort;
    }

    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(dbServerPort)) {
            logger.info("Starting DBServer on port: {}", dbServerPort);

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
            dbServerMsClient.handle(receivedMessage);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }
}
