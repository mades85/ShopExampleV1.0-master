package org.example.shop.model;

/*
* This class represents a product
* @author Daniel Klenn
* @version 1.0
* @since 1.0
 */

public class Product {
    protected long id;
    protected String name;
    protected String image;
    protected double rating;
    protected int numberOfRatings;
    protected double actualPrice;
    protected double discountPrice;

    public Product(long id, String name, String image, double rating, int numberOfRatings, double actualPrice, double discountPrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.actualPrice = actualPrice;
        this.discountPrice = discountPrice;
    }

    public Product() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getRating() {
        return rating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public static final int SHORTNAME_LENGTH = 33;

    public String getShortName() {
        int end = name.indexOf(' ', SHORTNAME_LENGTH);
        return name.substring(0, end) + "...";
    }
}