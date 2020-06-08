package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import services.files.IService;

@SpringBootApplication
@ComponentScan(basePackages = "services")
public class Main {

    public static void main(String... args) {

        SpringApplication.run(Main.class, args);


    }
}
