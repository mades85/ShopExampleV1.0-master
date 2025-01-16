package org.example.shop.controllers;

import org.apache.coyote.Request;
import org.example.shop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    private final static Logger LOG = LogManager.getLogger(CartController.class);
    protected static final String MESSAGE = "message";

    @Autowired
    CartService cartService;

    @GetMapping(value = "/add/{productId}")
    public String addToCart(
            @PathVariable(name = "productId") Integer productId, RedirectAttributes atts) {
        String productName = cartService.addProduct(productId);
        if (productName != null) {
            String message = String.format("'%s' added to the cart", productName);
            atts.addFlashAttribute(MESSAGE, message);
            LOG.info(message);
        } else {
            String message = String.format("Product with ID '%s' could not be found", productId);
            atts.addFlashAttribute(MESSAGE, message);
            LOG.warn(message);
        }
        //String refferer = request.getHeader("referer");
        return "redirect:/index.html";
    }

    @GetMapping(value = {"/increase/{productId}"})
    public String increaseQuantity(@PathVariable(name = "productId") Integer productId) {
        boolean isSuccesful = cartService.increaseQuantity(productId);
        if (isSuccesful) {
            LOG.info("Quantity of cart item with ID '{}' increased", productId);
        } else {
            LOG.warn("Product with ID '{}' could not be found", productId);
        }
            return "redirect:/cart.html";
    }

    @GetMapping(value = "/decrease/{productId}")
    public String decreaseQuantity(@PathVariable(name = "productId") Integer productId) {
        boolean isSuccesful = cartService.decreaseQuantity(productId);
        if (isSuccesful) {
            LOG.info("Quantity of cart item with ID '{}' decreased", productId);
        } else {
            LOG.warn("Product with ID '{}' could not be found", productId);
        }
        return "redirect:/cart.html";
    }

    @GetMapping(value = "/delete/{productID}")
    public String deleteFromCart(@PathVariable(name = "productID") Integer productId) {
        boolean isSuccesful = cartService.deleteItem(productId);
        if (isSuccesful) {
            LOG.info("Cart item with ID '{}' deleted", productId);
        } else {
            LOG.warn("Product with ID '{}' could not be found", productId);
        }
        return "redirect:/cart.html";
    }
}
