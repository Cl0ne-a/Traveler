package com.reactive.traveler;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class DataLoader {
    private final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;
    private final ReactiveRedisOperations<String, Traveller> reactiveRedisOperations;

    public DataLoader(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory, ReactiveRedisOperations<String, Traveller> reactiveRedisOperations) {
        this.reactiveRedisConnectionFactory = reactiveRedisConnectionFactory;
        this.reactiveRedisOperations = reactiveRedisOperations;
    }

    @PostConstruct
    public void loadData() {
        reactiveRedisConnectionFactory.getReactiveConnection().serverCommands().flushAll().thenMany(
                        Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
                                .map(name -> new Traveller(UUID.randomUUID().toString(), name))
                                .flatMap(coffee -> reactiveRedisOperations.opsForValue().set(coffee.getId(), coffee)))
                .thenMany(reactiveRedisOperations.keys("*")
                        .flatMap(reactiveRedisOperations.opsForValue()::get))
                .subscribe(System.out::println);
    }
}
