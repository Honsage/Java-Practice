package ru.honsage.practice.rx.deliveryservice.model;

import java.time.LocalDateTime;

public record Delivery(
        int id,
        Darkstore darkstore,
        LocalDateTime deliveredAt,
        Status status
) {}
