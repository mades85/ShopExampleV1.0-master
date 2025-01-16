package org.example.shop.model;

public class CartItem extends Product {
    private int quantity;

    public CartItem() {
        super();
        quantity = 1;
    }

    public CartItem(long id, String name, String image, double rating, int numberOfRating, double actualPrice,
                    double discountPrice) {
        super(id, name, image, rating, numberOfRating, actualPrice, discountPrice);
        quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        // never falls below zero
        if (quantity > 0) {
            quantity--;
        }

        // TODO : adjust stock number

    }

    public double getTotalPrice() {
        return Math.floor(quantity * discountPrice * 100) / 100;
    }

    // TODO : convert Product into cartItem
    public CartItem fromProduct(Product product) {
        CartItem cartItem = (CartItem) product;
        cartItem.setQuantity(1);
        return cartItem;
    }
    public String toString() {
        return this.getShortName();
    }

    public boolean getShowQuantity() {
        return quantity > 1;
    }
}
