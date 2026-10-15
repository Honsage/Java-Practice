package ru.honsage.practice.rx.deliveryservice.pipeline;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.honsage.practice.rx.deliveryservice.dto.DeliveryEvent;
import ru.honsage.practice.rx.deliveryservice.dto.PricedEvent;
import ru.honsage.practice.rx.deliveryservice.dto.SupplierTotal;
import ru.honsage.practice.rx.deliveryservice.model.Status;
import ru.honsage.practice.rx.deliveryservice.util.Logger;

import java.util.concurrent.TimeUnit;

public class DeliveryPipeline {

    public Flowable<SupplierTotal> build(Flowable<DeliveryEvent> dataSource) {
        return dataSource
                .onErrorResumeNext(err -> {
                    Logger.logError(err);
                    return Flowable.empty();
                })
                .filter(de -> de.quantity() > 0)
                .filter(de -> de.status() != Status.CANCELLED)
                .filter(de -> de.product() != null && de.product().price() > 0)
                .doOnNext(Logger::logDelivery)
                .map(e -> new PricedEvent(e, e.quantity() * e.product().price()))
                .doOnNext(Logger::logPriced)
                .groupBy(pe -> pe.event().supplierId())
                .flatMap(group -> group
                        .subscribeOn(Schedulers.computation())
                        .reduce(
                                SupplierTotal.empty(group.getKey()),
                                SupplierTotal::plus
                        )
                        .toFlowable()
                        .doOnNext(Logger::logTotalBySupplier)
                )
                .delay(300, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.single());
    }
}
