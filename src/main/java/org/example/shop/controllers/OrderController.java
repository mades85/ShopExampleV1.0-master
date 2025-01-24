package org.example.shop.controllers;

import org.example.shop.Constants;
import org.example.shop.model.Billing;
import org.example.shop.model.CartItem;
import org.example.shop.model.Order;
import org.example.shop.services.CartService;
import org.example.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value="/order")
public class OrderController {
    private final static Logger LOG = Logger.getLogger(OrderController.class.getName());

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;



    @PostMapping(value = "/place")
    public String placeOrder(Model viewModel,
                             @ModelAttribute Billing billing,
                             RedirectAttributes atts) {

        Order order = new Order(cartService.getCart().getItems(), LocalDateTime.now(), billing);
        int orderid = orderService.addOrder(order);
        cartService.getCart().getItems().clear();

        String message = String.format("Created order with ID %d", orderid);
        atts.addFlashAttribute(Constants.MESSAGE, message);
        LOG.info(message);
        return "redirect:/order/" + orderid;
    }

    @GetMapping(value = "/{id}")
    public String showOrder(Model viewModel,
                            @PathVariable(name = "id") Integer id,
                            RedirectAttributes atts) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            viewModel.addAttribute("order", order);
            LOG.info("Showing order with ID '{}'" + id);
            return "order";
        } else {
            String message = String.format("Order with ID %d not found", id);
            atts.addFlashAttribute(Constants.MESSAGE, message);
            LOG.warning(message);
            return "redirect:/shop.html";
        }
    }
}
