package ru.honsage.practice.rx.deliveryservice;

import io.reactivex.rxjava3.core.Flowable;
import ru.honsage.practice.rx.deliveryservice.dto.DeliveryEvent;
import ru.honsage.practice.rx.deliveryservice.dto.SupplierTotal;
import ru.honsage.practice.rx.deliveryservice.pipeline.DeliveryPipeline;
import ru.honsage.practice.rx.deliveryservice.source.DeliveryDataSources;
import ru.honsage.practice.rx.deliveryservice.subscriber.DeliverySubscriber;

import java.util.concurrent.TimeUnit;

public class DeliveryService {

    private static final int GENERATION_PERIOD_MS = 200;

    public static void run() {
        Flowable<DeliveryEvent> dataSource = DeliveryDataSources.intervalFlowable(GENERATION_PERIOD_MS);
        Flowable<SupplierTotal> pipeline = new DeliveryPipeline().build(dataSource);
        DeliverySubscriber subscriber = new DeliverySubscriber();

        pipeline.subscribe(subscriber);

        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        subscriber.cancel();
    }
}
