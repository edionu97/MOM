package ui.impl;

import controllers.IUserInterfaceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ui.IUserInterface;

@Component
@ComponentScan(basePackages = "controllers")
public class UserInterface implements IUserInterface {

    private final IUserInterfaceController controller;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public UserInterface(final IUserInterfaceController controller) {
        this.controller = controller;
    }

    @Override
    public void showUI() {

    }

    private void onMessage(final String message) {

    }
}
