package com.test;

import com.inventory.Inventory;
import com.woolsworth.Fresh;
import com.woolsworth.NonPerishable;
import com.woolsworth.Preserved;
import com.woolsworth.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/* JUnit Test */
public class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    // ── Adding Products ──

    @Test
    void addProductSuccessfully() {
        Product milk = new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7);
        assertTrue(inventory.addProduct(milk));
    }

    @Test
    void addMultipleBatchesOfSameProduct() {
        Product batch1 = new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7);
        Product batch2 = new Fresh("P001", "B002", LocalDate.now(),
                "Milk", 4.50, 15, 5);
        assertTrue(inventory.addProduct(batch1));
        assertTrue(inventory.addProduct(batch2));
    }

    @Test
    void rejectDuplicateBatch() {
        Product batch1 = new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7);
        Product duplicate = new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 10, 7);
        assertTrue(inventory.addProduct(batch1));
        assertFalse(inventory.addProduct(duplicate));
    }

    @Test
    void addDifferentProductTypes() {
        Product fresh = new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7);
        Product preserved = new Preserved("P002", "B002", LocalDate.now(),
                "Canned Beans", 3.00, 50, 365);
        Product nonPerishable = new NonPerishable("P003", "B003", LocalDate.now(),
                "Laptop", 1200.00, 5, 2);
        assertTrue(inventory.addProduct(fresh));
        assertTrue(inventory.addProduct(preserved));
        assertTrue(inventory.addProduct(nonPerishable));
    }

    // ── Discontinue Products ──

    @Test
    void discontinueExistingProduct() {
        Product milk = new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7);
        inventory.addProduct(milk);
        assertTrue(inventory.discontinueProduct("P001"));
    }

    @Test
    void discontinueNonExistentProduct() {
        assertFalse(inventory.discontinueProduct("P999"));
    }

    // ── Quantity Metrics ──

    @Test
    void totalQuantityAcrossAllProducts() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        inventory.addProduct(new NonPerishable("P002", "B002", LocalDate.now(),
                "Laptop", 1200.00, 5, 2));
        assertEquals(25, inventory.getTotalQuantity());
    }

    @Test
    void quantityByProductAcrossBatches() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        inventory.addProduct(new Fresh("P001", "B002", LocalDate.now(),
                "Milk", 4.50, 15, 5));
        assertEquals(35, inventory.getQuantityByProduct("P001"));
    }

    @Test
    void quantityForNonExistentProductReturnsNegOne() {
        assertEquals(-1, inventory.getQuantityByProduct("P999"));
    }

    // ── Value Metrics ──

    @Test
    void totalValueCalculation() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        inventory.addProduct(new NonPerishable("P002", "B002", LocalDate.now(),
                "Laptop", 1200.00, 5, 2));
        // (5 * 20) + (1200 * 5) = 100 + 6000 = 6100
        assertEquals(6100.00, inventory.getTotalValue(), 0.01);
    }

    @Test
    void valueByProductAcrossBatches() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        inventory.addProduct(new Fresh("P001", "B002", LocalDate.now(),
                "Milk", 4.50, 15, 5));
        // (5 * 20) + (4.5 * 15) = 100 + 67.5 = 167.5
        assertEquals(167.50, inventory.getValueByProduct("P001"), 0.01);
    }

    @Test
    void valueForNonExistentProductReturnsNegOne() {
        assertEquals(-1, inventory.getValueByProduct("P999"), 0.01);
    }

    @Test
    void emptyInventoryHasZeroValue() {
        assertEquals(0.0, inventory.getTotalValue(), 0.01);
    }

    // ── Expiry Handling ──

    @Test
    void expiredProductsExcludedFromQuantity() {
        // batch date 10 days ago, shelf life 5 days = expired
        Product expired = new Fresh("P001", "B001",
                LocalDate.now().minusDays(10), "Old Milk", 5.00, 20, 5);
        inventory.addProduct(expired);
        assertEquals(0, inventory.getTotalQuantity());
    }

    @Test
    void expiredProductsExcludedFromValue() {
        Product expired = new Fresh("P001", "B001",
                LocalDate.now().minusDays(10), "Old Milk", 5.00, 20, 5);
        inventory.addProduct(expired);
        assertEquals(0.0, inventory.getTotalValue(), 0.01);
    }

    @Test
    void mixOfExpiredAndValidProducts() {
        Product expired = new Fresh("P001", "B001",
                LocalDate.now().minusDays(10), "Old Milk", 5.00, 20, 5);
        Product valid = new Fresh("P001", "B002", LocalDate.now(),
                "Fresh Milk", 5.00, 10, 7);
        inventory.addProduct(expired);
        inventory.addProduct(valid);
        assertEquals(10, inventory.getQuantityByProduct("P001"));
        assertEquals(50.00, inventory.getValueByProduct("P001"), 0.01);
    }

    // ── Order Processing ──

    @Test
    void processOrderSuccessfully() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        assertTrue(inventory.processOrder("P001", "B001", 5));
        assertEquals(15, inventory.getQuantityByProduct("P001"));
    }

    @Test
    void processOrderInsufficientStock() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        assertFalse(inventory.processOrder("P001", "B001", 25));
        assertEquals(20, inventory.getQuantityByProduct("P001"));
    }

    @Test
    void processOrderNonExistentProduct() {
        assertFalse(inventory.processOrder("P999", "B001", 5));
    }

    @Test
    void processOrderWrongBatch() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        assertFalse(inventory.processOrder("P001", "B999", 5));
    }

    // ── isExpired on individual products ──

    @Test
    void freshProductNotExpired() {
        Product milk = new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7);
        assertFalse(milk.isExpired());
    }

    @Test
    void freshProductExpired() {
        Product milk = new Fresh("P001", "B001",
                LocalDate.now().minusDays(10), "Milk", 5.00, 20, 5);
        assertTrue(milk.isExpired());
    }

    @Test
    void nonPerishableNotExpired() {
        Product laptop = new NonPerishable("P003", "B003", LocalDate.now(),
                "Laptop", 1200.00, 5, 2);
        assertFalse(laptop.isExpired());
    }

    @Test
    void nonPerishableExpired() {
        Product laptop = new NonPerishable("P003", "B003",
                LocalDate.now().minusYears(3), "Laptop", 1200.00, 5, 2);
        assertTrue(laptop.isExpired());
    }

    // ── toString ──

    @Test
    void toStringContainsProductInfo() {
        inventory.addProduct(new Fresh("P001", "B001", LocalDate.now(),
                "Milk", 5.00, 20, 7));
        String output = inventory.toString();
        assertTrue(output.contains("P001"));
        assertTrue(output.contains("B001"));
    }
}
