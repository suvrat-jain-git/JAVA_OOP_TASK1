package com.test;

import com.inventory.Inventory;
import com.woolsworth.Fresh;
import com.woolsworth.NonPerishable;

import java.time.LocalDate;

class RunInventory {
    public static void main(String[] args) {
        Inventory i1 = new Inventory();
        NonPerishable p1 = new NonPerishable("CDM","b1",
                LocalDate.of(2026,3,22),"DairyMilk",
                10,20,1 );
        NonPerishable p2 = new NonPerishable("CDM","b2",
                LocalDate.of(2026,3,25),"DairyMilk",
                8,50,1 );
        NonPerishable p3 = new NonPerishable("CDM","b3",
                LocalDate.of(2026,3,28),"DairyMilk",
                12,10,1 );
        NonPerishable p4 = new NonPerishable("Lindt","b1",
                LocalDate.of(2026,3,28),"DarkChocolate",
                12,10,1 );
        Fresh p5 = new Fresh("FRT","b1",LocalDate.of(2026,4,1),
                "Banana",20,7,2);

        i1.addProduct(p1);
        i1.addProduct(p2);
        i1.addProduct(p3);
        i1.addProduct(p4);
        i1.addProduct(p5);
        System.out.println(i1);
        System.out.println(i1.getTotalQuantity());
        System.out.println(i1.getTotalValue());
        i1.discontinueProduct("Lindt");
        System.out.println(i1);
        System.out.println(i1.getQuantityByProduct("CDM"));
        System.out.println((i1.getValueByProduct(("CDM"))));
        i1.processOrder("CDM","b1",5);
        System.out.println(i1);
    }

}