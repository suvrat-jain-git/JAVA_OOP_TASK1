package com.woolsworth;

import java.time.LocalDate;

/* NonPerishable class child of Product class (Inheritance)*/
public class NonPerishable extends Product{
    private final int warrantyYears;
    private final LocalDate expiryDate;

    public NonPerishable(String productId, String batchId, LocalDate batchDate,
                         String name, double price, int quantity, int warrantyPeriod) {
        super(productId, batchId, batchDate, name, price, quantity);
        this.warrantyYears = warrantyPeriod;
        this.expiryDate = this.getBatchDate().plusYears(this.warrantyYears);
    }

    public int getWarrantyYears() {
        return warrantyYears;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /* Child class implementing the abstract method */
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expiryDate);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | expiry_date: %s",this.expiryDate.toString());
    }
}
