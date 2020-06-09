package controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.IUserInterfaceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "config")
public class UserInterfaceController implements IUserInterfaceController {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserInterfaceController(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }



}
