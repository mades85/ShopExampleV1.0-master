package org.example.shop.services;

import org.example.shop.model.Cart;
import org.example.shop.model.CartItem;
import org.example.shop.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Delivers necessary services concerning the shopping cart and its items
 *
 * @author Matthias Wenning
 * @version 1.4
 * @since 1.4
 */
@Service
public class CartService {
    private final static Logger LOG = LogManager.getLogger(CartService.class);

    private Cart cart;
    private ProductService productService;

    public CartService() {
        cart = new Cart();
        productService = new ProductService();
    }

    public Cart getCart() {
        return cart;
    }

    /**
     * Converts a {@link Product} to a {@link CartItem}
     *
     * @param product the Product to convert
     * @return the resulting CartItem with quantity set to 1
     */
    public CartItem fromProduct(Product product) {
        if (product == null) {
            LOG.warn("fromProduct(): Product is null");
            return null;
        }

        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(product, cartItem);
        LOG.debug("Converted Product '{}' to CartItem", product.getShortName());
        return cartItem;
    }

    /**
     * If an item with this productId already exists, its quantity is increased.
     * If not, it is added to the cart.
     *
     * @param productId the id to search for
     * @return the short name of the product, or <code>null</code> if none is found
     */
    public String addProduct(long productId) {
        // 1. search for this item in the cart
        CartItem cartItem = findById(productId);

        // 2. if cartItem already exists in the cart
        if (cartItem != null) {
            // 3. then increase its quantity
            cartItem.increaseQuantity();
            LOG.debug("Quantity of '{}' increased", cartItem.getShortName());
        } else {
            // 2. if productId exists in the product range
            Product product = productService.getProductById(productId);
            if (product != null) {
                // 3. then convert it to CartItem
                cartItem = fromProduct(product);
                // 4. and add it to the cart
                cart.getItems().add(cartItem);
                LOG.debug("Product {} added to Cart", product.getShortName());
            } else {
                LOG.warn("Product with ID '{}' not found in product rage", productId);
                return null;
            }
        }
        return cartItem.getShortName();
    }

    /**
     * If an item with this productId exists, it is removed from the cart.
     * If not, <code>null</code> is returned.
     *
     * @param productId the id to search for
     * @return the short name of the product, or <code>null</code> if none is found
     */
    public String removeProduct(long productId) {
        CartItem cartItem = findById(productId);
        if (cartItem != null) {
            cart.getItems().remove(cartItem);
            LOG.debug("Item '{}' removed from cart", cartItem.getShortName());
            return cartItem.getShortName();
        }
        LOG.warn("Item with ID '{}' not found in cart", productId);
        return null;
    }

    /**
     * Finds a {@link CartItem} by its productId within the shopping cart
     *
     * @param productId the id of the {@link Product} to find
     * @return {@link CartItem} or null, if none is found
     */
    CartItem findById(long productId) {
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getId() == productId) {
                LOG.debug("Found product with ID '{}'", productId);
                return cartItem;
            }
        }
        LOG.error("Could not find product with ID '{}'", productId);
        return null;
    }

    /**
     * Finds an item in the cart and increases its quantity by 1
     *
     * @param productId the cartItem's id
     * @return <code>true</code> if succesful, <code>false</code> otherwise
     */
    public boolean increaseQuantity(long productId) {
        CartItem cartItem = findById(productId);
        if (cartItem != null) {
            cartItem.increaseQuantity();
            LOG.debug("Quantity of '{}' increased to: {}", cartItem.getShortName(), cartItem.getQuantity());
            return true;
        } else {
            LOG.warn("Product with ID '{}' not found in cart", productId);
            return false;
        }
    }

    /**
     * Finds an item in the cart and decreases its quantity by 1
     *
     * @param productId the cartItem's id
     * @return <code>true</code> if succesful, <code>false</code> otherwise
     */
    public boolean decreaseQuantity(long productId) {
        CartItem cartItem = findById(productId);
        if (cartItem != null) {
            cartItem.decreaseQuantity();
            LOG.debug("Quantity of '{}' decreased to: {}", cartItem.getShortName(), cartItem.getQuantity());
            return true;
        } else {
            LOG.warn("Product with ID '{}' not found in cart", productId);
            return false;
        }

    }
}