package com.woolsworth;

import java.time.LocalDate;

/* Perishable class is child of Product class but is abstract itself */
public abstract class Perishable extends Product{
    private final int shelfLifeDays;
    private final LocalDate expiryDate;

    public Perishable(String productId, String batchId, LocalDate batchDate,
                      String name, double price, int quantity, int shelfLifeDays) {
        super(productId, batchId, batchDate, name, price, quantity);
        this.shelfLifeDays = shelfLifeDays;
        this.expiryDate = this.getBatchDate().plusDays(this.shelfLifeDays);
    }

    public int getShelfLifeDays() {
        return shelfLifeDays;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | expiry_date: %s",this.expiryDate.toString());
    }

    abstract void storageInstructions();
}
