package org.example.shop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart with a {@link CartItem} list
 *
 * @author Matthias Wenning
 * @version 1.4
 * @since 1.4
 */
public class Cart {
    List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Iterates through the cart items and sums their quantities
     *
     * @return the total number of items, including quantities
     */
    public int getNumberOfItems() {
        int totalNumberOfItems = 0;
        for (CartItem item : items) {
            totalNumberOfItems += item.getQuantity();
        }
        return totalNumberOfItems;
    }

    /**
     * Iterates through the cart items and sums their prices
     *
     * @return the cart's grand total, considering quantities
     */
    public double getGrandTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        // cuts the result to 2 decimal digits
        return Math.floor(total * 100) / 100;
    }
}
