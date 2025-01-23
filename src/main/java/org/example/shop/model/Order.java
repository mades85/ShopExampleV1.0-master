package org.example.shop.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 * This class represents a Order
 *
 * @author Matthias Wenning
 * @version 1.7
 * @since 1.0
 */
public class Order {
    private static int orderCounter = 1;
    private int id;
    private List<CartItem> items;
    private LocalDateTime orderDate;
    private LocalDate deliveryDate;
    private Billing billing;
    private double subTotal;
    private double discount;
    private double shipping;
    private double includedTax;
    private double grandTotal;

    public Order( List<CartItem> items, LocalDateTime orderDate, Billing billing) {
        this.id = orderCounter++;
        this.items = items;
        this.orderDate = orderDate;
        this.billing = billing;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public void setIncludedTax(double includedTax) {
        this.includedTax = includedTax;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public int getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Billing getBilling() {
        return billing;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getShipping() {
        return shipping;
    }

    public double getIncludedTax() {
        return includedTax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }
}
