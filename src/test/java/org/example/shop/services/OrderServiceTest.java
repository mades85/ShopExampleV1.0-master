package org.example.shop.services;

import static org.junit.jupiter.api.Assertions.*;

import org.example.shop.enums.PaymentMethod;
import org.example.shop.model.Billing;
import org.example.shop.model.CartItem;
import org.example.shop.model.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderServiceTest {
    private OrderService orderService;
    private CartService cartService;

    public OrderServiceTest() {
        orderService = new OrderService();
        cartService = new CartService();
    }

    private Order createOrder() {
        List<CartItem> items = fillCart();
        Billing billing = createBilling();
        LocalDateTime orderDate = LocalDateTime.now();
        return new Order(items, orderDate, billing);
    }

    private List<CartItem> fillCart() {
        for (int i = 1; i <= 10; i++) {
            cartService.addProduct(i);
        }
        return cartService.getCart().getItems();
    }

    private Billing createBilling() {
        return new Billing(
                "Freddie",
                "Walker",
                "Wilhelmstr. 19",
                "69190",
                "Walldorf",
                "+49-177-123-4567",
                "freddie@walker.com",
                PaymentMethod.CREDITCARD
        );
    }

    @Test
    void addOrder_first() {
        Order order = createOrder();
        int orderId = orderService.addOrder(order);
        assertEquals(1, orderId);
        assertEquals(10, order.getItems().size());
    }

    @Test
    void getOrders_3_orders() {
        orderService.addOrder(createOrder());
        orderService.addOrder(createOrder());
        orderService.addOrder(createOrder());
        List<Order> orders = orderService.getOrders();
        assertNotNull(orders);
        assertEquals(3, orders.size());
    }

    @Test
    void getOrderById_5() {
        orderService.addOrder(createOrder());
        orderService.addOrder(createOrder());
        orderService.addOrder(createOrder());
        Order order = orderService.getOrderById(6);
        assertNotNull(order);
        assertEquals(6, order.getId());
    }
    @Test
    void getOrderById_invalid() {
        orderService.addOrder(createOrder());
        Order order = orderService.getOrderById(Integer.MAX_VALUE);
        assertEquals(null, order);
    }
}