package ru.honsage.practice.rx.deliveryservice.dto;

public record PricedEvent(
        DeliveryEvent event,
        double cost
) {}
