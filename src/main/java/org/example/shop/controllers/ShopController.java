package org.example.shop.controllers;

import org.example.shop.Constants;
import org.example.shop.enums.Category;
import org.example.shop.enums.Sorting;
import org.example.shop.model.Cart;
import org.example.shop.model.Product;
import org.example.shop.model.Order;
import org.example.shop.services.CartService;
import org.example.shop.services.Pagination;
import org.example.shop.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController implements ErrorController {
    private final static Logger LOG = LogManager.getLogger(ShopController.class);

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @GetMapping(value = {"/", "/index.html"})
    public String homepage(Model viewModel, HttpServletRequest request) {
        String referrer = request.getHeader("referer");
        List<Product> products = productService.getProducts();
        loadCartItems(viewModel);
        viewModel.addAttribute("products", products.subList(0, 12));
        return "index";
    }

    // FIXME: unite index and shop mapping
    @GetMapping(value = {"/shop.html"})
    public String shop(Model viewModel,
        @RequestParam(name = "category", defaultValue = "ALL") Category category,
        @RequestParam(name = "sort", defaultValue = "NAME_ASC") Sorting sort,
        @RequestParam(name = "page", defaultValue = "1") Integer page) {
        handleCatSortPage(viewModel, category, sort, page);
        loadCartItems(viewModel);
        return "shop";
    }

    @GetMapping(value = {"/product.html"})
    public String detailsPage(Model viewModel,
                              @RequestParam(name = "id") Integer id,
                              RedirectAttributes atts) {
        // TODO: 1. get the product with {id} from the ProductService
        Product product = productService.getProductById(id);

        if (product != null) {
            // TODO: 2. if it exists, add it to the viewModel as 'product'
            viewModel.addAttribute("product", product);
            LOG.info("Showing details of '{}'", product.getShortName());
        } else {
            // TODO: 3. if it doesn't, show an error message using 'atts.addFlashAttribute()'
            String message = String.format("Product with ID '%s' not found", id);
            atts.addFlashAttribute(Constants.MESSAGE, message);
            LOG.warn(message);
        }
        loadCartItems(viewModel);
        return "product";
    }

    @GetMapping(value = {"/checkout.html"})
    public String checkout(Model viewModel) {
        Cart cart = cartService.getCart();
        Order order = new Order(cart.getItems());
        viewModel.addAttribute("order", order);
        loadCartItems(viewModel);
        return "checkout";
    }

    @GetMapping(value = {"/{name}.html"})
    public String htmlMapping(Model viewModel, @PathVariable String name) {
        loadCartItems(viewModel);
        return name;
    }

    @RequestMapping("/error")
    public String handleError() {
        //do something like logging
        return "error";
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

    /**
     * Delivers the articles sublist corresponding to the selected page
     *
     * @param viewModel the {@link Model}
     * @param page      the current page {@link Integer}
     */
    void handleCatSortPage(Model viewModel, Category category, Sorting sort, int page) {
        // handle category
        List<Product> products = productService.getCategorizedSorted(category, sort, page);
        createCategories(viewModel, category);

        // handle sorting
        createSortEntries(viewModel, sort);

        // handle pagination
        Pagination pagination = productService.getPagination();
        createPaginationLinks(viewModel, pagination.getPage(), pagination.getPageCount());

        // add products to viewModel
        viewModel.addAttribute("products", products);
        viewModel.addAttribute("from", pagination.getFrom() + 1);
        viewModel.addAttribute("to", pagination.getTo());
        viewModel.addAttribute("numberOfProducts", pagination.getTotalNumber());
    }

    /**
     * Creates category entries and links
     *
     * @param viewModel
     */
    void createCategories(Model viewModel, Category currentCategory) {
        List<Category> categories = new ArrayList<>();
        for (Category category : Category.values()) {
            String isActive = category.equals(currentCategory) ? "active" : "";
            category.setActive(isActive);
            categories.add(category);
        }
        viewModel.addAttribute("categories", categories);
        viewModel.addAttribute("category", currentCategory);

    }

    /**
     * Creates the entries for sorting dropdown
     *
     * @param viewModel
     * @param currentSort
     */
    void createSortEntries(Model viewModel, Sorting currentSort) {
        List<Sorting> sortings = new ArrayList<>();
        for (Sorting sort : Sorting.values()) {
            String isSelected = sort.equals(currentSort) ? "selected" : "";
            sort.setSelected(isSelected);
            sortings.add(sort);
        }
        viewModel.addAttribute("sortings", sortings);
        viewModel.addAttribute("sort", currentSort);
    }

    /**
     * Creates numbers and links for pagination
     *
     * @param viewModel
     * @param page
     * @param pageCount
     */
    void createPaginationLinks(Model viewModel, int page, int pageCount) {
        Map<Integer, String> pages = new HashMap<>();
        for (int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {
            String active = (pageNumber == page) ? "active" : "";
            pages.put(pageNumber, active);
        }
        viewModel.addAttribute("pageCount", pageCount);
        viewModel.addAttribute("pages", pages.entrySet());
        viewModel.addAttribute("prevPage", Math.max(page - 1, 1));
        viewModel.addAttribute("nextPage", Math.min(page + 1, pageCount));
    }

}
