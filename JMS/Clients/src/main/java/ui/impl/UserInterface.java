package ui.impl;

import controllers.IUserInterfaceController;
import manager.IResourceManager;
import messages.request.impl.MiddlewareRequest;
import messages.response.IResponse;
import messages.response.impl.DepthListResponse;
import messages.response.impl.SimpleListResponse;
import messages.response.impl.abstracts.AbstractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ui.IUserInterface;
import utils.enums.OperationType;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.*;
import java.util.concurrent.Future;

@Component
@ComponentScan(basePackages = "controllers")
public class UserInterface implements IUserInterface {

    private final IUserInterfaceController controller;
    private final Map<String, Runnable> options;
    private final IResourceManager resourceManager;


    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public UserInterface(final IUserInterfaceController controller, final IResourceManager resourceManager) {

        this.controller = controller;
        this.controller.setOnMessage(this::onMessage);
        this.resourceManager = resourceManager;

        options = this.buildMenu();
    }

    @Override
    @SuppressWarnings("InfiniteRecursion")
    public void showUI() {

        var userOption = showMenuOptions();

        if (options.containsKey(userOption)) {
            options.get(userOption).run();
            showUI();
            return;
        }

        writeToConsole(new StringBuilder("Wrong option"), true);
        showUI();
    }


    /**
     * In this function the menu is built
     *
     * @return a dictionary that, as keys has a option and as value a function that will be executed when the user enters the key option
     */
    private Map<String, Runnable> buildMenu() {

        return new HashMap<>() {{

            put("1", () -> {
                writeToConsole(new StringBuilder("Enter name: "), false);
                controller.sendRequestMessage(new MiddlewareRequest() {{
                    setPayload(new Scanner(System.in).nextLine().trim());
                    setType(OperationType.FilterByName);
                }});
            });

            put("2", () -> {
                writeToConsole(new StringBuilder("Enter text: "), false);
                controller.sendRequestMessage(new MiddlewareRequest() {{
                    setPayload(new Scanner(System.in).nextLine().trim());
                    setType(OperationType.FilterByContent);
                }});
            });

            put("3", () -> {
                writeToConsole(new StringBuilder("Enter hex-bytes separated by spaces: "), false);
                controller.sendRequestMessage(new MiddlewareRequest() {{
                    setPayload(new Scanner(System.in).nextLine().trim());
                    setType(OperationType.FilterByBinary);
                }});
            });

            put("4", () -> controller.sendRequestMessage(new MiddlewareRequest() {{
                        setPayload(null);
                        setType(OperationType.FilterDuplicates);
                    }})
            );
        }};
    }

    /**
     * Shows the options
     *
     * @return the option that user introduces
     */
    private String showMenuOptions() {
        writeToConsole(new StringBuilder()
                .append("\nPress one of the options\n")
                .append("1. For filtering by filename\n")
                .append("2. For filtering by text content\n")
                .append("3. For filtering by binary content\n")
                .append("4. For filtering by duplicates\n"), true);

        writeToConsole(new StringBuilder("Your option is: "), false);
        return new Scanner(System.in).next();
    }

    /**
     * This is the callback that is called when a message is pushed to the client
     *
     * @param message: the message
     */
    private void onMessage(final IResponse message) {

        var response = (AbstractResponse) message;
        if (response instanceof SimpleListResponse) {
            resourceManager
                    .writeToResource(stringifyResponse(response.getOnRequest(), ((SimpleListResponse) response).getResponse()));
            return;
        }

        resourceManager
                .writeToResource(stringifyResponse(response.getOnRequest(), ((DepthListResponse) response).getResponse()));
    }

    //region Utils

    /**
     * This method is used in order to synchronous write to console
     *
     * @param builder:    a StringBuilder that contain information about what will be printed on screen
     * @param useNewLine: true ? the content is followed by enter otherwise nothing is appended to standard output
     */
    private synchronized void writeToConsole(final StringBuilder builder, boolean useNewLine) {

        if (useNewLine) {
            System.out.println(builder);
            System.out.flush();
            return;
        }

        System.out.print(builder);
        System.out.flush();
    }

    private StringBuilder stringifyResponse(final MiddlewareRequest request, final List<?> responseList) {

        //get the list representation
        var listRepresentation = stringifyList(responseList, 0).toString();

        //get the max number of characters from the longest line
        int maxLineCharacters = Arrays
                .stream(listRepresentation.split("\n"))
                .max(Comparator.comparingInt(String::length))
                .map(String::length)
                .get();

        var line = request.getPayload() != null
                ? String.format(
                    "The result of middleware call '%s' with payload '%s' is displayed below",
                    request.getType(),
                    request.getPayload())
                : String.format(
                        "The result of middleware call '%s' is displayed below", request.getType());
        maxLineCharacters = Math.max(maxLineCharacters, line.length());

        //create the response
        return new StringBuilder()
                .append("\n".repeat(2))
                .append("=".repeat(maxLineCharacters))
                .append("\n".repeat(2))
                .append(line)
                .append("\n".repeat(2))
                .append(listRepresentation)
                .append("=".repeat(maxLineCharacters))
                .append("\n".repeat(2));
    }

    /**
     * This method is used for  pretty print a list
     *
     * @param list:             the list that wil be printed
     * @param tabulatorIndices: the number of tabs
     * @return a string builder representing the list
     */
    private static StringBuilder stringifyList(List<?> list, int tabulatorIndices) {

        var builder = new StringBuilder();

        builder.append("    ".repeat(tabulatorIndices)).append("[\n");
        for (var listElement : list) {

            //if the element is a list than we treat element as list
            if (listElement instanceof List<?>) {
                builder.append(stringifyList((List<?>) listElement, tabulatorIndices + 1));
                continue;
            }

            builder.append("    ".repeat(tabulatorIndices + 1)).append(listElement).append("\n");
        }

        builder.append("    ".repeat(tabulatorIndices)).append("]\n");
        return builder;
    }
    //endregion
}
