package com.woolsworth;

import java.time.LocalDate;

/* Product ia an abstract class (Abstraction)
   No object creation of Product class */
public abstract class Product {
    private final String productId;
    private final String batchId;
    private final LocalDate batchDate;
    private final String name;
    private double price;
    private int quantity;

    public Product(String productId, String batchId, LocalDate batchDate,
                   String name, double price, int quantity) {
        this.productId = productId;
        this.batchId = batchId;
        this.batchDate = batchDate;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /* Instance variables made private,
       final variables assigned via constructor and
       associated getter and setter set up (Encapsulation) */
    public String getProductId() {
        return productId;
    }

    public String getBatchId() {
        return batchId;
    }

    public LocalDate getBatchDate() {
        return batchDate;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /* Overriding the toString method
       inherited from Object class */
    @Override
    public String toString() {
        return String.format("batch_id: %s | price: %.2f | quantity: %d",
                this.batchId,this.price,this.quantity);
    }

    /* Abstract method without implementation created,
       implementation needs to be defined by child classes */
    public abstract boolean isExpired();

}
