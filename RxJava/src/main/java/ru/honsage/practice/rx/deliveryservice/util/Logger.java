package ru.honsage.practice.rx.deliveryservice.util;

import ru.honsage.practice.rx.deliveryservice.dto.DeliveryEvent;
import ru.honsage.practice.rx.deliveryservice.dto.PricedEvent;
import ru.honsage.practice.rx.deliveryservice.dto.SupplierTotal;

public class Logger {

    public static void logSubscribed() {
        logInfo(
                "Subscriber has subscribed on data"
        );
    }

    public static void logDelivery(DeliveryEvent de) {
        logInfo(
                "Delivery from %s: %s of %d qty",
                de.supplierName(),
                de.product().name(),
                de.quantity()
        );
    }

    public static void logPriced(PricedEvent pe) {
        logInfo(
                "Cost of %s of %d qty = %.2f total",
                pe.event().product().name(),
                pe.event().quantity(),
                pe.cost()
        );
    }

    public static void logTotalBySupplier(SupplierTotal st) {
        logInfo(
                "Total goods from %s: %d qty with %.2f total",
                st.supplierName(),
                st.totalQuantity(),
                st.totalCost()
        );
    }

    public static void logOnNextSupplier(SupplierTotal st) {
        logInfo(
                "Supplier %s is processed",
                st.supplierName()
        );
    }

    public static void logCompleted() {
        logInfo(
                "Thread is completed"
        );
    }

    public static void logError(Throwable err) {
        System.err.printf(
                "[!!!] Fatal error: %s%n",
                err.getMessage()
        );
    }

    public static void logInfo(String template, Object... args) {
        String message = String.format(template, args);
        System.out.printf("[%s] %s%n", Thread.currentThread().getName(), message);
    }
}
