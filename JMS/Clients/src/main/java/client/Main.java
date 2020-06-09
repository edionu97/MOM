package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ui.IUserInterface;

@SpringBootApplication
@ComponentScan(basePackages = "config")
public class Main implements CommandLineRunner {

    private final ApplicationContext context;

    @Autowired
    public Main(final ApplicationContext context){
        this.context = context;
    }

    public static void main(final String ...args) {
       SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(final String... args) {
        context.getBean(IUserInterface.class).showUI();
    }
}
