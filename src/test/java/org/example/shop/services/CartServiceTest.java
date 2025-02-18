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

    public CartServiceTest() {
        cartService = new CartService();
        productService = new ProductService();
    }

    @Test
    void fromProduct_Xiaomi_11() {
        Product xiaomi11 = productService.getProductById(171);
        CartItem cartItem = cartService.fromProduct(xiaomi11);

        assertEquals(1, cartItem.getQuantity());
        assertEquals(4.39, cartItem.getDiscountPrice());
        assertEquals(10.99, cartItem.getTotalPrice());
    }

    @Test
    void addProduct_171() {
        cartService.addProduct(171);
        CartItem cartItem = cartService.findById(171);
        assertNotNull(cartItem);
        assertEquals(4.39, cartItem.getDiscountPrice());

    }

    @Test
    @DisplayName("fromProduct : non-existant productId")
    void fromProduct_nonExistantProductId() {
        Product nonExistant = productService.getProductById(999999);
        assertEquals(null, nonExistant);
    }

    @Test
    void addProduct_non_existant() {
        cartService.addProduct(9999);
        CartItem cartItem = cartService.findById(9999);
        assertEquals(null, cartItem);
    }

    @Test
    void increaseQuantity_171() {
        cartService.addProduct(171);
        cartService.increaseQuantity(171);
        cartService.increaseQuantity(171);
        CartItem cartItem = cartService.findById(171);
        assertEquals(3, cartItem.getQuantity());

    }

    @Test
    void increaseQuantity_non_existent() {
        cartService.addProduct(Integer.MAX_VALUE);
        cartService.increaseQuantity(Integer.MAX_VALUE);
        cartService.increaseQuantity(Integer.MAX_VALUE);
        CartItem cartItem = cartService.findById(Integer.MAX_VALUE);
        assertEquals(null, cartItem);
    }

    @Test
    void decreaseQuantity_171() {
        // add 4 times
        cartService.addProduct(171);
        cartService.addProduct(171);
        cartService.addProduct(171);
        cartService.addProduct(171);

        // decrease 1 time
        cartService.decreaseQuantity(171);
        CartItem cartItem = cartService.findById(171);
        assertEquals(3, cartItem.getQuantity());
    }

    @Test
    void decreaseQuantity_non_existent() {
        cartService.addProduct(Integer.MAX_VALUE);
        cartService.decreaseQuantity(Integer.MAX_VALUE);
        cartService.decreaseQuantity(Integer.MAX_VALUE);
        CartItem cartItem = cartService.findById(Integer.MAX_VALUE);
        assertEquals(null, cartItem);
    }

    @Test
    void removeProduct_171() {
        cartService.addProduct(171);
        String shortName = cartService.removeProduct(171);
        assertTrue(shortName.startsWith("XGMO"));
    }

        @Test
        void removeProduct_non_existent () {
            String shortName = cartService.removeProduct(Integer.MAX_VALUE);
            assertEquals(null, shortName);
        }
    }