package controllers;

import java.util.function.Consumer;

public interface IUserInterfaceController {

    /**
     * This method is used for setting the callback that handlers the message from middleware to client
     * @param handler: the callback
     */
    void setOnMessage(final Consumer<String> handler);
}
