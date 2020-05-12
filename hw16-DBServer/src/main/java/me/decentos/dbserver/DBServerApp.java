package me.decentos.dbserver;

import me.decentos.dbserver.server.DBServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DBServerApp implements CommandLineRunner {
    private final DBServer dbServer;

    public DBServerApp(DBServer dbServer) {
        this.dbServer = dbServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(DBServerApp.class, args);
    }

    @Override
    public void run(String... args) {
        dbServer.go();
    }
}
