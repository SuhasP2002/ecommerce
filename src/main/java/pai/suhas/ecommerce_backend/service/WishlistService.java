package pai.suhas.ecommerce_backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.AddToWishlistRequest;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.entity.User;
import pai.suhas.ecommerce_backend.entity.Wishlist;
import pai.suhas.ecommerce_backend.exception.ProductNotFoundException;
import pai.suhas.ecommerce_backend.repository.ProductRepository;
import pai.suhas.ecommerce_backend.repository.UserRepository;
import pai.suhas.ecommerce_backend.repository.WishlistRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService
{
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistService(
            WishlistRepository wishlistRepository,
            ProductRepository productRepository,
            UserRepository userRepository)
    {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Wishlist addToWishlist(AddToWishlistRequest request)
    {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        User user = getCurrentUser();

        Optional<Wishlist> existingWishlist =
                wishlistRepository.findByUserAndProduct(user, product);

        if(existingWishlist.isPresent())
        {
            return existingWishlist.get();
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setUser(user);
        wishlist.setProduct(product);

        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getWishlist()
    {
        User user = getCurrentUser();

        return wishlistRepository.findByUser(user);
    }

    public void removeFromWishlist(Long wishlistId)
    {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() ->
                        new RuntimeException("Wishlist item not found"));

        wishlistRepository.delete(wishlist);
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