package config;

import controllers.IUserInterfaceController;
import controllers.impl.UserInterfaceController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ui.IUserInterface;
import ui.impl.UserInterface;

@Configuration
public class Bootstrapper {

    @Bean
    public IUserInterfaceController interfaceController() {
        return new UserInterfaceController();
    }

    @Bean
    public IUserInterface userInterface() {
        return new UserInterface(interfaceController());
    }

}
