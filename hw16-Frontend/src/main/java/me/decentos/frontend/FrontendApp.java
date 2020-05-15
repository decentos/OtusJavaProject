package me.decentos.frontend;

import me.decentos.frontend.socketserver.FrontendSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontendApp implements CommandLineRunner {
    private final FrontendSocketServer frontendSocketServer;

    public FrontendApp(FrontendSocketServer frontendSocketServer) {
        this.frontendSocketServer = frontendSocketServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(FrontendApp.class, args);
    }

    @Override
    public void run(String... args) {
        frontendSocketServer.go();
    }
}
