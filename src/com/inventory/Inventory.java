package com.inventory;

import com.woolsworth.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Inventory class implements InventoryMetrics interface */
public class Inventory implements InventoryMetrics{
    /* inventory object is a hash map of key value pairs,
       wherein key is the product_id and value is a list of
       different batches (having varying expiry date) of the same product_id */
    private final Map<String, List<Product>> inventory;

    public Inventory() {
        this.inventory=new HashMap<>();
    }
    /* object methods available:
       1. add product to inventory
       2. remove discontinued product from inventory
       3. remove expired products from inventory (private method)
       4. get quantity by product, get total quantity
       5. get value by product, get total value
       6. process order from inventory if available*/

    public boolean addProduct(Product p){
        if (inventory.containsKey(p.getProductId())){
            List<Product> productList = inventory.get(p.getProductId());
            for (Product i : productList){
                if (i.getBatchId().equals(p.getBatchId())){
                    return false;
                }
            }
            productList.add(p);
        } else {
            inventory.put(p.getProductId(),new ArrayList<>());
            inventory.get(p.getProductId()).add(p);
        }
        return true;
    }

    public boolean discontinueProduct(String productId){
        return inventory.remove(productId)!=null;
    }

    private void discardExpiredProducts(){
        for (String key: inventory.keySet()){
            List<Product> productList = inventory.get(key);
            for (int i=0;i<inventory.get(key).size();i++){
                if (productList.get(i).isExpired()){
                    productList.remove(i);
                    i--;
                }
            }
        }
    }

    @Override
    public int getQuantityByProduct(String productId) {
        this.discardExpiredProducts();
        try {
            List<Product> productList = inventory.get(productId);
            int quantity = 0;
            for (Product p : productList) {
                quantity += p.getQuantity();
            }
            return quantity;
        } catch (NullPointerException e) {
            return -1;
        }
    }

    @Override
    public int getTotalQuantity() {
        this.discardExpiredProducts();
        int totalQuantity = 0;
        for (String key : inventory.keySet()){
            List<Product> productList = inventory.get(key);
            for (Product p : productList){
                totalQuantity+=p.getQuantity();
            }
        }
        return totalQuantity;
    }

    @Override
    public double getValueByProduct(String productId) {
        this.discardExpiredProducts();
        try {
            List<Product> productList = inventory.get(productId);
            double value = 0;
            for (Product p : productList) {
                value += (p.getQuantity() * p.getPrice());
            }
            return value;
        } catch (NullPointerException e){
            return -1;
        }
    }

    @Override
    public double getTotalValue() {
        this.discardExpiredProducts();
        double totalValue = 0;
        for (String key : inventory.keySet()){
            List<Product> productList = inventory.get(key);
            for (Product p : productList){
                totalValue+=p.getQuantity()*p.getPrice();
            }
        }
        return totalValue;
    }

    @Override
    public String toString() {
        this.discardExpiredProducts();
        StringBuilder sb = new StringBuilder();
        for (String key : inventory.keySet()) {
            sb.append("product_id: ").append(key).append("\n");
            for (Product p : inventory.get(key)) {
                sb.append("  ").append(p.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public boolean processOrder(String productId, String batchId, int buying){
        this.discardExpiredProducts();
        try {
            for (Product p : inventory.get(productId)) {
                if (p.getBatchId().equals(batchId)) {
                    if (p.getQuantity() >= buying) {
                        p.setQuantity(p.getQuantity() - buying);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } catch (NullPointerException e ){
            return false;
        }
    }
}
