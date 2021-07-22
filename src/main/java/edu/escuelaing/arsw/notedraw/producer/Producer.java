package edu.escuelaing.arsw.notedraw.producer;

import javax.inject.Inject;

import edu.escuelaing.arsw.notedraw.connection.PSRedisListenerContainer;
import edu.escuelaing.arsw.notedraw.connection.PSRedisTemplate;
import edu.escuelaing.arsw.notedraw.receiver.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;
@Component
public class Producer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Inject
    private ApplicationContext appContext;
    @Inject
    PSRedisTemplate template;
    @Inject
    Receiver receiver;

    @Inject
    PSRedisListenerContainer container;
    public void run(String... args) throws Exception {


        for( int i = 0; i <7 ; i++){
            Receiver receiver = appContext.getBean(Receiver.class);
            container.addMessageListener(receiver, new PatternTopic("PSChannel"));
        }

        Integer i =0;
        Thread.sleep(500);
        while (i < 6) {
            i= i+ 1;
            LOGGER.info("Sending message... " + i);
            template.convertAndSend("PSChannel", "Hello from Redis! Message " + i);
            Thread.sleep(500);
        }
        System.exit(0);
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public PSRedisTemplate getTemplate() {
        return template;
    }

    public void setTemplate(PSRedisTemplate template) {
        this.template = template;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public PSRedisListenerContainer getContainer() {
        return container;
    }

    public void setContainer(PSRedisListenerContainer container) {
        this.container = container;
    }
}
