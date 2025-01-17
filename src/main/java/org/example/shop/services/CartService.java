package org.example.shop.services;

import org.example.shop.controllers.CartController;
import org.example.shop.model.Cart;
import org.example.shop.model.CartItem;
import org.example.shop.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * delivers necassary Services concerning the cart its items
 *
 * @author Daniel Klenn
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
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(product, cartItem);
        return cartItem;
    }

    /**
     * Finds a product with the {@link ProductService} and adds it to the cart
     *
     * @param productId the id to search for
     * @return <code>true</code>, if successful
     */

    public String addProduct(long productId) {
        // 1. Search for this Item in the cart
        CartItem cartItem = findById(productId);
        // 2. If cartItem already exists in the cart
        if (cartItem != null) {
            // 3. the increase the quantity
            cartItem.increaseQuantity();
            LOG.debug("Quantity of '{}' increased", cartItem.getShortName());
        } else {
            // 2. if prdouctId exists in the product range
            Product product = productService.getProductById(productId);
            if (product != null) {
                LOG.info("Product '{}' added to cart", product.getShortName());
                // 3. then convert it into cartItem
                cartItem = fromProduct(product);
                // 4. and add it to the cart
                cart.getItems().add(cartItem);
            } else {
                LOG.debug("Product with ID '{}' not found in product range", productId);
                return null;
            }
        }
        // TODO :  else output error massage in error Page
        return cartItem.toString();
    }


    /**
     * Finds a {@link CartItem} by its productId within the same shopping cart
     *
     * @param productId the id of the {@link Product} to find
     * @return {@link CartItem}
     */

    CartItem findById(long productId) {
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getId() == productId) {
                return cartItem;
            }
        }
        return null;
    }

    /**
     * Finds a {@link CartItem} by its productId and increased its quantity by 1
     * @param productId
     * @return true on success
     */
    public boolean increaseQuantity(long productId) {
        CartItem cartItem = findById(productId);
        if (cartItem != null) {
            cartItem.increaseQuantity();
            LOG.debug("Quantity of '{}' increased", cartItem.getShortName());
            return true;

        } else {
            LOG.debug("Product with ID '{}' not found in cart", productId);
            return false;
        }

    }
    /**
     * Finds a {@link CartItem} by its productId and decrease its quantity by 1
     * @param productId
     * @return false on success
     */
    public boolean decreaseQuantity(long productId) {
        CartItem cartItem = findById(productId);
        if (cartItem != null) {
            cartItem.decreaseQuantity();
            LOG.debug("Quantity of '{}' decreased", cartItem.getShortName());
            return true;

        } else {
            LOG.debug("Product with ID '{}' not found in cart", productId);
            return false;
        }
    }
    /**
     * Finds a {@link CartItem} by its productId and delete it from the cart
     * @param productId
     * @return true on success
     */
    public boolean deleteItem(long productId) {
        for (CartItem cartItem : cart.getItems()) {
             if (cartItem.getId() == productId) {
                cart.getItems().remove(cartItem);
                LOG.debug("Delete of '{}'", cartItem.getShortName());
                return true;
            }
        }
        LOG.debug("Product with ID '{}' not found in cart", productId);
        return false;
    }

}
