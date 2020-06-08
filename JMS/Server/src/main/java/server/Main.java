package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "services")
public class Main {
    public static void main(final String... args) {
        SpringApplication.run(Main.class, args);
    }
}
