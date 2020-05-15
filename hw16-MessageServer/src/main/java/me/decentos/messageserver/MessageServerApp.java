package me.decentos.messageserver;

import me.decentos.messageserver.server.MessageServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageServerApp implements CommandLineRunner {

    private final MessageServer messageServer;

    public MessageServerApp(MessageServer messageServer) {
        this.messageServer = messageServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageServerApp.class, args);
    }

    @Override
    public void run(String... args) {
        messageServer.go();
    }
}
