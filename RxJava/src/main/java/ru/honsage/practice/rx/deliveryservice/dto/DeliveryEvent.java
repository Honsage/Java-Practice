package ru.honsage.practice.rx.deliveryservice.dto;

import ru.honsage.practice.rx.deliveryservice.model.Good;
import ru.honsage.practice.rx.deliveryservice.model.Status;

import java.time.LocalDateTime;

public record DeliveryEvent(
        int supplierId,
        String supplierName,
        Good product,
        int quantity,
        Status status,
        LocalDateTime deliveredAt
) {}
