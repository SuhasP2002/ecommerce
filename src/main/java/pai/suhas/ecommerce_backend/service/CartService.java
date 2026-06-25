package pai.suhas.ecommerce_backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.AddToCartRequest;
import pai.suhas.ecommerce_backend.entity.Cart;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.entity.User;
import pai.suhas.ecommerce_backend.exception.InsufficientStockException;
import pai.suhas.ecommerce_backend.exception.ProductNotFoundException;
import pai.suhas.ecommerce_backend.repository.CartRepository;
import pai.suhas.ecommerce_backend.repository.ProductRepository;
import pai.suhas.ecommerce_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService
{
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,ProductRepository productRepository,UserRepository userRepository)
    {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart addToCart(AddToCartRequest request)
    {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found " + request.getProductId()));

        if(request.getQuantity() > product.getStockQuantity())
        {
            throw new InsufficientStockException(
                    "Not enough stock available");
        }
        // Temporary hardcoded user
        // Later we will get this from JWT
        User user = getCurrentUser();

        Optional<Cart> existingCart = cartRepository.findByUserAndProduct(user,product);

        if (existingCart.isPresent())
        {
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            return cartRepository.save(cart);
        }

        Cart cart = new Cart();

        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());

        return cartRepository.save(cart);
    }

    public List<Cart> getCart()
    {
        User user = getCurrentUser();

        return cartRepository.findByUser(user);
    }

    public void removeFromCart(Long cartId)
    {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));

        cartRepository.delete(cart);
    }
    private User getCurrentUser()
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}