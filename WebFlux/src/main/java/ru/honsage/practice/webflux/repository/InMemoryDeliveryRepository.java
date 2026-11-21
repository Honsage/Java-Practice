package ru.honsage.practice.webflux.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.honsage.practice.webflux.model.Delivery;
import ru.honsage.practice.webflux.model.Status;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryDeliveryRepository {

    private final Map<Long, Delivery> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Flux<Delivery> findAll() {
        return Flux.fromIterable(store.values());
    }

    public Mono<Delivery> findById(Long id) {
        return Mono.justOrEmpty(store.get(id));
    }

    public Mono<Delivery> save(Delivery delivery) {
        long id = delivery.id() != null ? delivery.id() : idGen.getAndIncrement();
        Delivery saved = new Delivery(id, delivery.darkstore(), delivery.status());
        store.put(id, saved);
        return Mono.just(saved);
    }

    public Mono<Void> deleteById(Long id) {
        store.remove(id);
        return Mono.empty();
    }
}





