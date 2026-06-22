package pai.suhas.ecommerce_backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.AddToCartRequest;
import pai.suhas.ecommerce_backend.entity.Cart;
import pai.suhas.ecommerce_backend.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController
{
    private final CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart addToCart(
            @Valid @RequestBody AddToCartRequest request)
    {
        return cartService.addToCart(request);
    }

    @GetMapping
    public List<Cart> getCart()
    {
        return cartService.getCart();
    }

    @DeleteMapping("/{cartId}")
    public String removeFromCart(@PathVariable Long cartId)
    {
        cartService.removeFromCart(cartId);
        return "Cart item removed successfully";
    }
}