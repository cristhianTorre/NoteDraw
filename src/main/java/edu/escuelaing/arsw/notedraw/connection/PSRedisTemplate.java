package edu.escuelaing.arsw.notedraw.connection;

import javax.inject.Inject;
import javax.management.MXBean;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
@Component
public class PSRedisTemplate extends StringRedisTemplate{
    @Inject
    public PSRedisTemplate(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }
}
