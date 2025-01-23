package org.example.shop.model;

import org.example.shop.enums.Category;

/**
 * This class represents a product
 *
 * @author Matthias Wenning
 * @version 1.7
 * @since 1.0
 */
public class Product {
    public static final int SHORTNAME_LENGTH = 33;
    // will be generated automatically
    protected long id;
    protected String name;
    Category category;
    protected String image;
    protected double rating;
    protected int numberOfRatings;
    protected double actualPrice;
    protected double discountPrice;

    public Product() {}

    public Product(long id, String name, String image, double rating, int numberOfRatings, double actualPrice, double discountPrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.actualPrice = actualPrice;
        this.discountPrice = discountPrice;
    }

    public Product(long id, String name, Category category, String image, double rating, int numberOfRatings, double actualPrice, double discountPrice) {
        this(id, name, image, rating, numberOfRatings, actualPrice, discountPrice);
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        int end = name.indexOf(' ', SHORTNAME_LENGTH);
        end = Math.max(end, SHORTNAME_LENGTH);
        return name.substring(0, end) + "...";
    }

    public Category getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public double getRating() {
        return rating;
    }

    public String[] getRatingStars() {
        String[] stars = new String[5];
        // fills x stars with "solid" until the rating is reached
        for (int i = 1; i <= 5; i++) {
            String starType = (i <= rating) ? "solid" : "outline";
            stars[i - 1] = starType;
        }
        return stars;
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

    public void setCategory(Category category) {
        this.category = category;
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
}
