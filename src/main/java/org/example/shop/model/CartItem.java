package org.example.shop.model;

/**
 * Extends the {@link Product} by a quantity
 *
 * @author Matthias Wenning
 * @version 1.4
 * @since 1.4
 */
public class CartItem extends Product {
    private int quantity;

    public CartItem() {
        super();
        quantity = 1;
    }

    public CartItem(long id, String name, String image, double rating, int numberOfRatings, double actualPrice, double discountPrice) {
        super(id, name, image, rating, numberOfRatings, actualPrice, discountPrice);
        quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getShowQuantity() {
        return quantity > 1;
    }

    public void increaseQuantity() {
        quantity++;
        // TODO: take stock into account
    }

    public void decreaseQuantity() {
        // never falls below zero
        if (quantity > 0) {
            quantity--;
        }
        // TODO: adjust stock number
    }

    public double getTotalPrice() {
        double total = quantity * actualPrice;
        return Math.floor(total * 100) / 100;
    }

}
