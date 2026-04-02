package com.woolsworth;

import java.time.LocalDate;

public class Preserved extends Perishable{
    static final boolean isColdStored=false;

    public Preserved(String productId, String batchId, LocalDate batchDate,
                     String name, double price, int quantity, int shelfLifeDays) {
        super(productId, batchId, batchDate, name, price, quantity, shelfLifeDays);
    }

    @Override
    void storageInstructions() {
        System.out.println(Preserved.isColdStored
                ? "Keep refrigerated at 1-4°C"
                : "Store in dry place, away from moisture");
    }
}
