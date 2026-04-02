package com.woolsworth;

import java.time.LocalDate;

/* Fresh class child of Perishable class and
   grand child of Product class (Multi Level Inheritance) */
public class Fresh extends Perishable{
    /* Static variable accessed via class */
    static final boolean isColdStored=true;

    public Fresh(String productId, String batchId, LocalDate batchDate,
                 String name, double price, int quantity, int shelfLifeDays) {
        super(productId, batchId, batchDate, name, price, quantity, shelfLifeDays);
    }

    @Override
    void storageInstructions() {
        System.out.println(Fresh.isColdStored
                ? "Keep refrigerated at 1-4°C"
                : "Store in dry place, away from moisture");
    }
}
