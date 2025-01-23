package org.example.shop.services;

import org.example.shop.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private List<Order> orders;

    public OrderService() {
        this.orders = new ArrayList<Order>();
    }

    public int addOrder(Order order) {
        this.orders.add(order);
        return order.getId();
    }
    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }
}
