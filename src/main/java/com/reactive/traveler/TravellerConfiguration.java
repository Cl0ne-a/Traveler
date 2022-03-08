package com.reactive.traveler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class TravellerConfiguration {
    @Bean
    ReactiveRedisOperations<String, Traveller> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Traveller> serializer = new Jackson2JsonRedisSerializer<>(Traveller.class);

        RedisSerializationContext
                .RedisSerializationContextBuilder<String, Traveller> builder =
                RedisSerializationContext
                        .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Traveller> context =
                builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
