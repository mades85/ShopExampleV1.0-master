package org.example.shop.model;


import org.example.shop.services.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CartItemTest {
    private ProductService productService = new ProductService();

    CartItem cartItem;

    public CartItemTest(){
        this.cartItem= new CartItem( 171 ,
                "Xiaomi 11 Lite NE 5G " ,
                "https://m.media-amazon.com/images/I/71XmlboxNZL._AC_UL320_.jpg ",
                4.0 ,
                25175,
                373.99,
                285.99);
    }

    @Test
    void getTotalPrice() {
        cartItem.setQuantity(3);
        assertEquals(857.97, cartItem.getTotalPrice());
    }

    @Test
    @DisplayName("CartItem : default quantity is 1")
    void decreaseQuantity_defaultValue(){
        assertEquals(1, cartItem.getQuantity());
    }

    @Test
    @DisplayName("DecreaseQuantity from default to 0")
    void decreaseQuantity_setToZero(){
        cartItem.decreaseQuantity();
        assertEquals(0,cartItem.getQuantity());
    }

    @Test
    @DisplayName("DecreaseQuantity never falls below zero")
    void decreaseQuantity_never_falls_below_zero() {
        assertEquals(1, cartItem.getQuantity());
        cartItem.decreaseQuantity();
        assertEquals(0, cartItem.getQuantity());
        cartItem.decreaseQuantity();
        assertEquals(0, cartItem.getQuantity());
    }
}