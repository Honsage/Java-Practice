package ru.honsage.practice.rx.deliveryservice.source;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.honsage.practice.rx.deliveryservice.dto.DeliveryEvent;
import ru.honsage.practice.rx.deliveryservice.model.Darkstore;
import ru.honsage.practice.rx.deliveryservice.model.Good;
import ru.honsage.practice.rx.deliveryservice.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DeliveryDataSources {

    private static final Random randomGenerator = new Random();

    private static final int BACKPRESSURE_CAPACITY = 50;

    private static final List<Darkstore> SUPPLIERS = List.of(
            new Darkstore(1, "Склад Москва"),
            new Darkstore(2, "Склад СПб"),
            new Darkstore(3, "Склад Пермь")
    );

    private static final List<Good> GOODS = List.of(
            new Good(1, "Молоко",89.90f),
            new Good(2, "Хлеб", 45.90f),
            new Good(3, "Сыр", 320.00f),
            new Good(4, "Масло", 210.00f),
            new Good(5, "Шоколад", 79.50f)
    );

    public static Flowable<DeliveryEvent> intervalFlowable(long periodMs) {
        return Flowable.interval(periodMs, TimeUnit.MILLISECONDS, Schedulers.io())
                .map(seq -> generateDelivery())
                .onBackpressureBuffer(BACKPRESSURE_CAPACITY);
    }

    private static DeliveryEvent generateDelivery() {
        Darkstore supplier = SUPPLIERS.get(randomGenerator.nextInt(SUPPLIERS.size()));
        Good good = GOODS.get(randomGenerator.nextInt(GOODS.size()));
        int quantity = randomGenerator.nextInt(20);
        Status status = Status.values()[randomGenerator.nextInt(Status.values().length)];
        return new DeliveryEvent(
                supplier.id(),
                supplier.address(),
                good,
                quantity,
                status,
                LocalDateTime.now()
        );
    }
}
