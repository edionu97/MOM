package services.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import services.invokers.IInvokerService;
import services.message.msg.Message;

import javax.jms.Queue;
import java.util.HashMap;

@Component
@ComponentScan(basePackages = "config")
public class JmsMessageService {

    private final JmsTemplate jmsTemplate;
    private final Queue middlewareToClient;

    private final ObjectMapper mapper;
    private final IInvokerService invokerService;

    @Autowired
    public JmsMessageService(final JmsTemplate jmsTemplate,
                             final Queue middlewareToClient,
                             final IInvokerService invokerService) {

        this.jmsTemplate = jmsTemplate;
        this.middlewareToClient = middlewareToClient;

        this.mapper = new ObjectMapper() {{
            enable(SerializationFeature.INDENT_OUTPUT);
        }};

        this.invokerService = invokerService;
    }

    @JmsListener(destination = "client-to-middleware")
    public void onRequest(final String message) throws Exception {
        invokerService.invoke(mapper.readValue(message, Message.class), this::onResponse);
    }

    /**
     * This method is used as callback, when the MOM processes the request
     *
     * @param message: the message that will be sent to client
     */
    public void onResponse(final Object message, final Message requestMessage) {
        try {

            var jsonData = mapper.writeValueAsString(
                    new HashMap<String, Object>() {{
                        put("response", message);
                        put("onRequest", requestMessage);
                    }}
            );

            jmsTemplate.convertAndSend(middlewareToClient, jsonData);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
