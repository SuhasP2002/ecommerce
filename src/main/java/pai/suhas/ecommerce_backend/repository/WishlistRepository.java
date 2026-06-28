package pai.suhas.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.entity.User;
import pai.suhas.ecommerce_backend.entity.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>
{
    List<Wishlist> findByUser(User user);

    Optional<Wishlist> findByUserAndProduct(User user, Product product);
}