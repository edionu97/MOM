package ui.impl;

import controllers.IUserInterfaceController;
import ui.IUserInterface;

public class UserInterface implements IUserInterface {

    private final IUserInterfaceController controller;

    public UserInterface(final IUserInterfaceController controller) {
        this.controller = controller;
    }

    @Override
    public void showUI() {

    }
}
