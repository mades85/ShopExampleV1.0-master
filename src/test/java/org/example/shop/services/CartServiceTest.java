package org.example.shop.services;

import org.example.shop.model.CartItem;
import org.example.shop.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {
    @Autowired
    private CartService cartService;
    private ProductService productService;

    public CartServiceTest(){
        cartService = new CartService();
        productService = new ProductService();
    }

    @Test
    void fromProduct_Xiaomi_11() {
        Product xiaomi11 = productService.getProductById(171);
        CartItem cartItem = cartService.fromProduct(xiaomi11);

        assertEquals(1,cartItem.getQuantity());
        assertEquals(285.99, cartItem.getDiscountPrice());
        assertEquals(285.99,cartItem.getTotalPrice());
    }

    @Test
    void addProduct_171() {
        cartService.addProduct(171);
        CartItem cartItem = cartService.findById(171);
        assertNotNull(cartItem);
        assertEquals(285.99,cartItem.getDiscountPrice());

    }

    @Test
    @DisplayName("fromProduct : non-existant productId")
    void fromProduct_nonExistantProductId() {
        Product nonExistant = productService.getProductById(999999);
        assertEquals(null,nonExistant);
    }

    @Test
    void addProduct_non_existant(){
        cartService.addProduct(9999);
        CartItem cartItem = cartService.findById(9999);
        assertEquals(null,cartItem);
    }
}