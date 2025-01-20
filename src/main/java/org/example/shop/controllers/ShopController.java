package org.example.shop.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController implements ErrorController {
    private final static Logger LOG = LogManager.getLogger(CartController.class);
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
        viewModel.addAttribute("products", products.subList(0, 8));
        return "index";
    }

    @GetMapping(value = {"/shop.html"})
    public String shop(Model viewModel, @RequestParam(name = "page", required = false) Integer page,
                       @RequestParam(name = "sort", required = false) String sort) {

        // validierung der Seitenzahl
        int maxPages = productService.getNumberOfProducts() / productService.PAGE_SIZE + 1;
        page = page == null ? 1 : (page < maxPages ? page : maxPages);
        page = page < 1 ? 1 : page;

        // zu zeigende Produktrange ermitteln
        int from = Math.max((page - 1) * productService.PAGE_SIZE, 0);
        int to = Math.min(productService.getNumberOfProducts(), from + productService.PAGE_SIZE);
        productService.sortArticles(sort);

        LOG.info("showing page " + page + " of " + maxPages + " pages");
        LOG.info("getting Items from " + from + " to " + to);
        viewModel.addAttribute("products", productService.getProductsRange(from, to));
        viewModel.addAttribute("from", from);
        viewModel.addAttribute("to", to);
        viewModel.addAttribute("numberOfProducts", productService.getNumberOfProducts());
        buildPageNumbers(viewModel, page);
        loadCartItems(viewModel);

        return "shop";
    }

    @GetMapping(value = {"/{name}.html"})
    public String htmlMapping(@PathVariable String name, Model viewModel) {
        loadCartItems(viewModel);
        return name;
    }

    @GetMapping(value = {"/single-product.html"})
    public String singleProduct(Model model, @RequestParam(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("message", "Product not found");
            LOG.warn("Product not found with id {}", id);
            return "redirect:/index.html";
        } else {
            model.addAttribute("product", product);
            LOG.info("Product with id {} found", id);
        }
        loadCartItems(model);
        return "single-product";
    }

    /**
     * Loads the cart items from the cart object and stores the corresponding attributes in the viewModel viewModel.
     *
     * @param viewModel {@link Model}
     */

    void loadCartItems(Model viewModel) {
        Cart cart = cartService.getCart();
        viewModel.addAttribute("cartItems", cart.getItems());
        viewModel.addAttribute("numOfCartItems", cart.getNumberOfItems());
        viewModel.addAttribute("grandTotal", cart.getGrandTotal());
    }

    void buildPageNumbers(Model viewModel, int page) {
        int pageCount = (productService.getProducts().size() / productService.PAGE_SIZE)+ 1;
        Map<Integer, String> pages = new HashMap<>();
        for (int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {
            String active = (pageNumber == page) ? "active" : "";
            pages.put(pageNumber, active);
        }
        viewModel.addAttribute("pages", pages.entrySet());
        viewModel.addAttribute("pageCount", pageCount);
        viewModel.addAttribute("prevPage", (page == 1)? page : page - 1);
        viewModel.addAttribute("nextPage", (page == pageCount) ? page :page + 1);
    }
}
