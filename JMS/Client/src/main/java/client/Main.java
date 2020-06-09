package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
        new SpringApplicationBuilder(Main.class){{
            headless(false);
            run(args);
        }};
    }

    @Override
    public void run(final String... args) {
        userInterface.showUI();
    }
}
