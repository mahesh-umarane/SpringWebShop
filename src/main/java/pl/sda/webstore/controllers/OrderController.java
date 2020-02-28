package pl.sda.webstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.webstore.services.OrderService;

@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/order/P1234/2")
    public String process(){
        orderService.processOrder("P1234", 2);
        return "redirect:/products";
    }
}
