package org.example.shop.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
@Component
@SessionScope
/** Represents a shopping cart ... */
public class Cart {
    List<CartItem> items;
    int totalNumberOfItems;

    public Cart(){
        items = new ArrayList<>();
    }
    public Cart(List<CartItem> items){
        this.items = items;
    }
    public List<CartItem> getItems(){
        return items;
    }

    public int getNumberOfItems(){
        int totalNumberOfItems = 0;
        for(CartItem item : items){
            totalNumberOfItems += item.getQuantity();
        }
        return totalNumberOfItems;
    }

    public double getGrandTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return Math.floor(total * 100) / 100;
    }
}

