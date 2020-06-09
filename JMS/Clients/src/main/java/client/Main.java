package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ui.IUserInterface;

@SpringBootApplication
@ComponentScan(basePackages = {"config", "ui"})
public class Main implements CommandLineRunner {

    private final IUserInterface userInterface;

    @Autowired
    public Main(final IUserInterface userInterface){
        this.userInterface = userInterface;
    }

    public static void main(final String ...args) {
       SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(final String... args) {
        userInterface.showUI();
    }
}
