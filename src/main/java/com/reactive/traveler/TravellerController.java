package com.reactive.traveler;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TravellerController {
    private final TravellerRepository travellerRepository;
    private final ReactiveRedisOperations<String, Traveller> reactiveRedisOperations;

    public TravellerController(TravellerRepository travellerRepository, ReactiveRedisOperations<String, Traveller> reactiveRedisOperations) {
        this.travellerRepository = travellerRepository;
        this.reactiveRedisOperations = reactiveRedisOperations;
    }

    @GetMapping("/travellers")
    public Flux<Traveller> getTravellers() {
        return this.travellerRepository.findAll();
    }

    @GetMapping("/travellers/{id}")
    public Mono<Traveller> findById(@PathVariable String id) {
        return this.travellerRepository.findById(id);
    }

    @PostMapping("/travellers")
    public Mono<Long> save(@RequestBody Traveller traveller) {
        return  this.travellerRepository.save(traveller);
    }

    @DeleteMapping("/travellers/{id}")
    public Mono<Long> deleteById(@PathVariable String id) {
        return this.travellerRepository.deleteById(id);
    }
}
