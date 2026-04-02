# 🛒 Woolsworth Inventory Management System

A Java OOP assignment modelling a supermarket-style inventory system for perishable, preserved, and non-perishable products — complete with expiry handling, batch tracking, and order processing.

---

## 📐 Class Hierarchy

```
Product  (abstract)
├── NonPerishable
└── Perishable  (abstract)
    ├── Fresh
    └── Preserved
```

---

## 📦 Package Structure

```
src/
├── com.woolsworth/
│   ├── Product.java           # Abstract base class
│   ├── NonPerishable.java     # Warranty-based expiry (years)
│   ├── Perishable.java        # Abstract; shelf-life-based expiry (days)
│   ├── Fresh.java             # Cold-stored perishable
│   └── Preserved.java         # Dry-stored perishable
└── com.inventory/
    ├── InventoryMetrics.java  # Interface for quantity/value metrics
    └── Inventory.java         # Core inventory logic
```

---

## 🧠 OOP Concepts Demonstrated

| Concept | Where |
|---|---|
| **Abstraction** | `Product` and `Perishable` are abstract; `isExpired()` and `storageInstructions()` are abstract methods |
| **Encapsulation** | All fields are `private`; `final` fields are set via constructors; access via getters/setters |
| **Inheritance** | `NonPerishable` and `Perishable` extend `Product`; `Fresh` and `Preserved` extend `Perishable` (multi-level) |
| **Polymorphism** | `isExpired()` is overridden in `NonPerishable` (warranty years) and `Perishable` (shelf-life days); `toString()` overridden at each level |
| **Interface** | `Inventory` implements `InventoryMetrics` (quantity/value contracts) |
| **Static variables** | `isColdStored` in `Fresh` and `Preserved` for class-level storage type |

---

## 🗄️ Inventory Data Structure

The `Inventory` class uses a `HashMap<String, List<Product>>`:

- **Key** → `productId` (e.g. `"P001"`)
- **Value** → `List<Product>` of different batches of that product

This allows multiple batches of the same product (with different expiry dates and prices) to coexist.

---

## ⚙️ Core Features

### Product Management
- `addProduct(Product p)` — Adds a new batch; rejects duplicate batch IDs
- `discontinueProduct(String productId)` — Removes a product and all its batches

### Automatic Expiry Handling
- Expired batches are silently discarded before any read operation via the private `discardExpiredProducts()` method

### Inventory Metrics *(via `InventoryMetrics` interface)*
- `getQuantityByProduct(String productId)` — Total units across all valid batches
- `getTotalQuantity()` — Total units across all products
- `getValueByProduct(String productId)` — Total value (price × quantity) for a product
- `getTotalValue()` — Total value across all products
- Returns `-1` for quantity / `-1.0` for value when a product ID does not exist

### Order Processing
- `processOrder(String productId, String batchId, int quantity)` — Deducts from a specific batch if stock is sufficient; returns `false` otherwise

---

## 🧪 Running the Tests

Tests are written with **JUnit 5** and live in `InventoryTest.java` (`com.test` package). Run directly from your IDE by right-clicking the file and selecting **Run 'InventoryTest'**.

### Test Coverage

| Category | Tests |
|---|---|
| Adding products | Successful add, multiple batches, duplicate rejection, mixed types |
| Discontinuing products | Existing and non-existent product IDs |
| Quantity metrics | Total, by product, non-existent product |
| Value metrics | Total, by product, non-existent product, empty inventory |
| Expiry handling | Expired excluded from quantity/value; mixed valid + expired batches |
| Order processing | Successful order, insufficient stock, wrong product/batch |
| `isExpired()` | Fresh and NonPerishable — both expired and valid cases |
| `toString()` | Inventory output contains correct product/batch info |

---

## ▶️ Quick Demo

`InventoryRun.java` (`com.test` package) runs a manual demo:

```java
Inventory i1 = new Inventory();

// Add 3 batches of Cadbury DairyMilk (NonPerishable, 1-year warranty)
i1.addProduct(new NonPerishable("CDM", "b1", LocalDate.of(2026,3,22), "DairyMilk", 10, 20, 1));
i1.addProduct(new NonPerishable("CDM", "b2", LocalDate.of(2026,3,25), "DairyMilk",  8, 50, 1));
i1.addProduct(new NonPerishable("CDM", "b3", LocalDate.of(2026,3,28), "DairyMilk", 12, 10, 1));

// Add Lindt and a Fresh banana batch
i1.addProduct(new NonPerishable("Lindt", "b1", LocalDate.of(2026,3,28), "DarkChocolate", 12, 10, 1));
i1.addProduct(new Fresh("FRT", "b1", LocalDate.of(2026,4,1), "Banana", 20, 7, 2));

System.out.println(i1);                        // Print full inventory
System.out.println(i1.getTotalQuantity());     // Total units
System.out.println(i1.getTotalValue());        // Total value

i1.discontinueProduct("Lindt");                // Remove Lindt
i1.processOrder("CDM", "b1", 5);              // Buy 5 units from batch b1
```

---

## 📋 Dependencies

| Dependency | Purpose |
|---|---|
| Java 11+ | `java.time.LocalDate` |
| JUnit 5 (`org.junit.jupiter`) | Unit testing |

---

## 👤 Author
SUVRAT JAIN<br>
Woolsworth Inventory Management — Java OOP Assignment
