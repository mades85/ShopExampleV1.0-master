package org.example.shop.enums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a product category
 *
 * @author Matthias Wenning
 * @version 1.7
 * @since 1.7
 */
public enum Category {
    ALL("All"),
    CAMERA("Camera & Photo"),
    CAR("Car & Vehicle Electronics"),
    PHONES("Cell Phones & Accessories"),
    COMPUTERS("Computers & Accessories"),
    HEADPHONES("Headphones"),
    AUDIO("Portable Audio & Video"),
    SMARTPHONES("Smartphones");

    private final static Logger LOG = LogManager.getLogger(Category.class);
    private final String label;
    private String active = "";

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    /**
     * Returns the corresponding category for a given label
     * @param label the proper, case-insensitive label
     * @return the corresponding category
     */
    public static Category fromLabel(String label) {
        for (Category category : Category.values()) {
            if (category.label.equalsIgnoreCase(label)) {
                return category;
            }
        }
        LOG.warn("Category with label '{}' doesn't exist!", label);
        return PHONES;
    }
}
