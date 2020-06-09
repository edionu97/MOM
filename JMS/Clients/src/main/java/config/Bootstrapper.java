package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import manager.IResourceManager;
import manager.impl.ResourceManager;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

@Configuration
public class Bootstrapper {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${queue.name.middleware.name}")
    private String clientToMiddlewareQueueName;

    @Value("${result-file.name}")
    private String propertyFileName;

    @Value("${open-result}")
    private boolean openAfterWrite;

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper(){{
            enable(SerializationFeature.INDENT_OUTPUT);
        }};
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        var factory =  new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        return factory;
    }

    @Bean
    public Queue serverClientQueue(){
        return new ActiveMQQueue(clientToMiddlewareQueueName);
    }

    @Bean
    public JmsTemplate template(){
        return new JmsTemplate(activeMQConnectionFactory());
    }

    @Bean
    public IResourceManager resourceManager() {
        return new ResourceManager(propertyFileName, openAfterWrite){{
            clearResource();
        }};
    }

}
