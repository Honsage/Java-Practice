package ru.honsage.practice.webflux.model;

public record Delivery(
        Long id,
        Darkstore darkstore,
        Status status
) {}
