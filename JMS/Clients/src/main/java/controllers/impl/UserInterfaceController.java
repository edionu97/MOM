package controllers.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.IUserInterfaceController;
import messages.request.IRequest;
import messages.response.IResponse;
import messages.response.impl.DepthListResponse;
import messages.response.impl.SimpleListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.function.Consumer;

@Component
@ComponentScan(basePackages = "config")
public class UserInterfaceController implements IUserInterfaceController {

    private final ObjectMapper objectMapper;
    private Consumer<IResponse> messageHandler;
    private final JmsTemplate template;
    private final Queue messageQueue;

    @Autowired
    public UserInterfaceController(final ObjectMapper objectMapper,
                                   final JmsTemplate template, final Queue queue) {
        this.objectMapper = objectMapper;
        this.messageQueue = queue;
        this.template = template;
    }


    @Override
    public void setOnMessage(final Consumer<IResponse> handler) {
        this.messageHandler = handler;
    }

    @Override
    public void sendRequestMessage(final IRequest request) {
        try {
            template.convertAndSend(messageQueue, objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a message from middleware to client is received
     * @param message: the received message
     */
    @JmsListener(destination = "middleware-to-client")
    protected void onMessage(final String message) {

        if(messageHandler == null) {
            return;
        }

        //parse the response
        IResponse response = null;
        try {
            response = objectMapper.readValue(message, SimpleListResponse.class);
        } catch (JsonProcessingException e) {
            try {
                response = objectMapper.readValue(message, DepthListResponse.class);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }

        messageHandler.accept(response);
    }

}
