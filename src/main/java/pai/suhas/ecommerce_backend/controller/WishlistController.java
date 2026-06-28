package pai.suhas.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.AddToWishlistRequest;
import pai.suhas.ecommerce_backend.entity.Wishlist;
import pai.suhas.ecommerce_backend.service.WishlistService;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController
{
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService)
    {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public Wishlist addToWishlist(@RequestBody AddToWishlistRequest request)
    {
        return wishlistService.addToWishlist(request);
    }

    @GetMapping
    public List<Wishlist> getWishlist()
    {
        return wishlistService.getWishlist();
    }

    @DeleteMapping("/{id}")
    public void removeFromWishlist(@PathVariable Long id)
    {
        wishlistService.removeFromWishlist(id);
    }
}