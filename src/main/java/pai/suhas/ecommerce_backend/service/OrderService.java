package pai.suhas.ecommerce_backend.service;

import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.OrderResponse;
import pai.suhas.ecommerce_backend.entity.*;
import pai.suhas.ecommerce_backend.exception.CartEmptyException;
import pai.suhas.ecommerce_backend.exception.InsufficientStockException;
import pai.suhas.ecommerce_backend.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService
{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            UserRepository userRepository, ProductRepository productRepository)
    {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public OrderResponse placeOrder()
    {
        // Temporary hardcoded user
        User user = userRepository.findByEmail("suhas@gmail.com")
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty())
        {
            throw new CartEmptyException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        double totalAmount = 0;

        for (Cart cart : cartItems)
        {
            totalAmount += cart.getQuantity() * cart.getProduct().getPrice();
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        for (Cart cart : cartItems)
        {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cart.getProduct());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setPrice(cart.getProduct().getPrice());

            orderItemRepository.save(orderItem);

            Product product = cart.getProduct();
            if(cart.getQuantity() > product.getStockQuantity())
            {
                throw new InsufficientStockException("Not enough stock available for " + product.getName());
            }
            product.setStockQuantity((product.getStockQuantity())-cart.getQuantity());
            productRepository.save(product);
        }

        cartRepository.deleteAll(cartItems);

        return mapToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getOrders()
    {
        User user = userRepository.findByEmail("suhas@gmail.com")
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    private OrderResponse mapToOrderResponse(Order order)
    {
        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setOrderDate(order.getOrderDate());

        return response;
    }
}