package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import services.IService;

@SpringBootApplication
@ComponentScan(basePackages = "services")
public class Main {

    public static void main(String... args) {

        var context = SpringApplication.run(Main.class, args);


        var service = context.getBean(IService.class);

        var duplicates = service.findDuplicateFiles();


    }
}
