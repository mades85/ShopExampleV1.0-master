package org.example.shop.enums;

public enum Sorting {
    NAME_ASC("Name (A-Z)"),
    NAME_DESC("Name (Z-A)"),
    PRICE_ASC("Price (low-high)"),
    PRICE_DESC("Price (high-low)"),
    RATING_ASC("Rating (low-high)"),
    RATING_DESC("Rating (high-low)");

    private final String label;
    private String selected = "";

    Sorting(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
