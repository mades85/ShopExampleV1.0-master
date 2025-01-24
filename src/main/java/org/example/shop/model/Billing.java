package org.example.shop.model;

import org.example.shop.enums.PaymentMethod;
/**
 * Represents a shopping cart with a {@link Billing}
 *
 * @author Matthias Wenning
 * @version 1.4
 * @since 1.4
 */
public class Billing {

    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String city;
    private String phoneNumber;
    private String email;
    PaymentMethod paymentMethod;

    public Billing (String firstName, String lastName, String address, String zipCode, String city,
                    String phoneNumber, String email, PaymentMethod paymentMethod) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.paymentMethod = paymentMethod;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
