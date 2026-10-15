package ru.honsage.practice.rx.deliveryservice.subscriber;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import org.reactivestreams.Subscription;
import ru.honsage.practice.rx.deliveryservice.dto.DeliveryEvent;
import ru.honsage.practice.rx.deliveryservice.dto.SupplierTotal;
import ru.honsage.practice.rx.deliveryservice.util.Logger;

public class DeliverySubscriber implements FlowableSubscriber<SupplierTotal> {

    private Subscription subscription;

    @Override
    public void onSubscribe(@NonNull Subscription s) {
        this.subscription = s;
        Logger.logSubscribed();
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(SupplierTotal supplierTotal) {
        Logger.logOnNextSupplier(supplierTotal);
    }

    @Override
    public void onError(Throwable t) {
        Logger.logError(t);
    }

    @Override
    public void onComplete() {
        Logger.logCompleted();
    }

    public void cancel() {
        if (subscription != null)
            subscription.cancel();
    }
}
