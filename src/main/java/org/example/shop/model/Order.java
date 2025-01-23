package org.example.shop.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.example.shop.Constants.*;

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

    public Order(List<CartItem> items) {
        this.items = items;
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

    public String getSubTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        subTotal = total;
        return trimDecimals(subTotal);
    }

    public String getDiscount() {
        discount = DISCOUNT_FACTOR * subTotal;
        return trimDecimals(discount);
    }

    public String getShipping() {
        shipping = SHIPPING_COSTS;
        return trimDecimals(shipping);
    }

    public String getIncludedTax() {
        includedTax = VAT_FACTOR * subTotal;
        return trimDecimals(includedTax);
    }

    public String getGrandTotal() {
        grandTotal = subTotal + includedTax + shipping - discount;
        return trimDecimals(grandTotal);
    }

    public String trimDecimals(double value) {
        return String.format(Locale.US, "%.2f", value);
    }
}
