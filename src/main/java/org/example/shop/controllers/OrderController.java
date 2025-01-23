package org.example.shop.controllers;

import org.example.shop.Constants;
import org.example.shop.model.Billing;
import org.example.shop.model.Order;
import org.example.shop.services.CartService;
import org.example.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
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

        String message = String.format("Created order with ID %d", orderid);
        atts.addFlashAttribute(Constants.MESSAGE, message);
        LOG.info(message);
        return "redirect:/shop.html";
    }
}
