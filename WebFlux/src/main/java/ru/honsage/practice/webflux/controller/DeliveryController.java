package ru.honsage.practice.webflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.honsage.practice.webflux.model.Delivery;
import ru.honsage.practice.webflux.service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Delivery> getAll() {
        return service.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public Mono<Delivery> getById(@PathVariable Long id) {
        return service.getDeliveryById(id);
    }

    @GetMapping(value = "/{id}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getByIdStream(@PathVariable Long id) {
        return service.getDeliveryStreamById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Delivery> create(@RequestBody Delivery delivery) {
        return service.createDelivery(delivery);
    }

    @PutMapping("/{id}")
    public Mono<Delivery> update(@PathVariable Long id, @RequestBody Delivery delivery) {
        return service.updateDelivery(id, delivery);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.deleteDelivery(id);
    }
}