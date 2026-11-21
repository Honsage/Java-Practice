package ru.honsage.practice.webflux.service;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.honsage.practice.webflux.model.Delivery;
import ru.honsage.practice.webflux.model.Status;
import ru.honsage.practice.webflux.repository.InMemoryDeliveryRepository;

import java.time.Duration;

@Service
public class DeliveryService {

    private static final String ORDERS_URL = "http://localhost:8081";
    private static final String ORDER_ID_URI = "/orders/{id}";

    private final InMemoryDeliveryRepository repository;
    private final WebClient webClient;

    public DeliveryService(InMemoryDeliveryRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder.baseUrl(ORDERS_URL).build();
    }

    public Flux<Delivery> getAllDeliveries() {
        return repository.findAll()
                .filter(d -> d.status() != Status.CANCELLED);
    }

    public Mono<Delivery> getDeliveryById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Delivery not found: " + id)))
                .onErrorResume(e -> {
                    System.err.println("[ERROR] " + e.getMessage());
                    return Mono.empty();
                });
    }

    public Flux<ServerSentEvent<String>> getDeliveryStreamById(Long id) {
        return Flux.interval(Duration.ofSeconds(2))
                .take(5)
                .map(seq -> {
                    Status status = Status.values()[seq.intValue() % Status.values().length];
                    return ServerSentEvent.<String>builder()
                            .id(String.valueOf(seq))
                            .event("status-update")
                            .data("Delivery " + id + " status: " + status)
                            .build();
                });
    }

    public Mono<Delivery> createDelivery(Delivery delivery) {
        return webClient.get()
                .uri(ORDER_ID_URI, delivery.id())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("order-default"))
                .flatMap(orderInfo -> {
                    System.out.println("Received order info: " + orderInfo);
                    return repository.save(delivery);
                });
    }

    public Mono<Delivery> updateDelivery(Long id, Delivery delivery) {
        return repository.findById(id)
                .flatMap(existing -> {
                    Delivery updated = new Delivery(id, delivery.darkstore(), delivery.status());
                    return repository.save(updated);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Cannot update: not found")));
    }

    public Mono<Void> deleteDelivery(Long id) {
        return repository.deleteById(id);
    }
}
