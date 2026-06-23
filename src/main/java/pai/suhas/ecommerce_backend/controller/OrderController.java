package pai.suhas.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.OrderResponse;
import pai.suhas.ecommerce_backend.entity.Order;
import pai.suhas.ecommerce_backend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    private final OrderService orderService;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse placeOrder()
    {
        return orderService.placeOrder();
    }

    @GetMapping
    public List<OrderResponse> getOrders()
    {
        return orderService.getOrders();
    }
}