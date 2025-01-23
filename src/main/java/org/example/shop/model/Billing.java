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

    private String firstname;
    private String lastname;
    private String address;
    private String zipCode;
    private String city;
    private String phoneNumber;
    private String email;
    PaymentMethod paymentMethod;

    public Billing (String firstname, String lastname, String address, String zipCode, String city,
                    String phoneNumber, String email, PaymentMethod paymentMethod) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.paymentMethod = paymentMethod;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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
