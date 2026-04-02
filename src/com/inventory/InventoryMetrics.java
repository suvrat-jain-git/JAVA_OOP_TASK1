package com.inventory;

public interface InventoryMetrics {
    int getTotalQuantity();
    double getTotalValue();
    int getQuantityByProduct(String productId);
    double getValueByProduct(String productId);
}
