package config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import repo.models.File;
import repo.repositories.IRepository;
import repo.repositories.impl.Repository;
import utils.search.ISearcher;
import utils.search.impl.KmpSearcher;

import javax.jms.Queue;

@Configuration
public class Bootstrapper {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${queue.middleware.client.name}")
    private String middlewareToClientQueue;

    @Bean
    public IRepository<File> directoryRepository(){
        return new Repository<>(File.class);
    }

    @Bean
    public ISearcher searcher(){
        return new KmpSearcher();
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        var factory =  new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        return factory;
    }

    @Bean
    public Queue serverClientQueue(){
        return new ActiveMQQueue(middlewareToClientQueue);
    }

    @Bean
    public JmsTemplate template(){
        return new JmsTemplate(activeMQConnectionFactory());
    }

}
