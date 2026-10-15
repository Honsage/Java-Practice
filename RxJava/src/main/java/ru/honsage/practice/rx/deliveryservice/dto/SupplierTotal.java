package ru.honsage.practice.rx.deliveryservice.dto;

public record SupplierTotal(
        int supplierId,
        String supplierName,
        double totalCost,
        int totalQuantity
) {

    public static SupplierTotal empty(int supplierId) {
        return new SupplierTotal(supplierId, "", 0.0, 0);
    }

    public SupplierTotal plus(PricedEvent pe) {
        return new SupplierTotal(
                supplierId,
                pe.event().supplierName(),
                totalCost + pe.cost(),
                totalQuantity + pe.event().quantity()
        );
    }
}
