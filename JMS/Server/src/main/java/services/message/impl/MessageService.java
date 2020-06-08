package services.message.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.invokers.IInvokerService;
import services.message.IMessageService;
import services.message.msg.Message;
import utils.enums.OperationType;
import utils.persistence.HibernateUtils;

import javax.jms.Queue;

@Component
@RestController
@RequestMapping("/message")
@ComponentScan(basePackages = "config")
public class MessageService implements IMessageService {

    private final JmsTemplate jmsTemplate;
    private final Queue responseQueue;
    private final ObjectMapper mapper;
    private final IInvokerService invokerService;

    @Autowired
    public MessageService(final JmsTemplate jmsTemplate,
                          final Queue responseQueue, final IInvokerService invokerService) {
        this.jmsTemplate = jmsTemplate;
        this.responseQueue = responseQueue;

        this.mapper = new ObjectMapper() {{
            enable(SerializationFeature.INDENT_OUTPUT);
        }};

        this.invokerService = invokerService;
    }

    @GetMapping("/")
    public String get() {

        try {
            jmsTemplate.convertAndSend(responseQueue,
                    mapper.writeValueAsString(new Message() {{
                        setPayload("ff d8 ff");
                        setOperationType(OperationType.FilterByBinary);
                    }}));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "Send";
    }

    @JmsListener(destination = "response.queue")
    public void listen(final String message) throws Exception {
        invokerService.invoke(mapper.readValue(message, Message.class));
    }

}
