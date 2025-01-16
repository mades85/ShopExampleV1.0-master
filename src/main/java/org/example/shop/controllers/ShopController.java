package org.example.shop.controllers;

import org.example.shop.model.Cart;
import org.example.shop.model.Product;
import org.example.shop.services.CartService;
import org.example.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Controller
public class ShopController implements ErrorController {
    @Autowired
    ProductService productService;
    @Autowired
    private CartService cartService;


    @GetMapping(value = {"/"})
    public String root(Model model) {
        return "redirect:/index.html";
    }

    @GetMapping(value = {"/index.html"})
    public String homepage(Model viewModel) {
        List<Product> products = productService.getProducts();
        loadCartItems(viewModel);
        viewModel.addAttribute("products", products.subList(0,8));
        return "index";
    }

    @GetMapping(value= {"/shop.html"})
    public String shop(Model viewModel) {
        List<Product> products = productService.getProducts();
        loadCartItems(viewModel);
        viewModel.addAttribute("products", products.subList(0, 15));
        return "shop";
    }

    @GetMapping(value = {"/{name}.html"})
    public String htmlMapping(@PathVariable String name, Model viewModel) {
        loadCartItems(viewModel);
        return name;
    }

    /**
     * Loads the cart items from the cart object and stores the corresponding attributes in the viewModel viewModel.
     * @param viewModel {@link Model}
     */

    void loadCartItems(Model viewModel) {
        Cart cart = cartService.getCart();
        viewModel.addAttribute("cartItems", cart.getItems());
        viewModel.addAttribute("numOfCartItems", cart.getNumberOfItems());
        viewModel.addAttribute("grandTotal", cart.getGrandTotal());
    }
}
