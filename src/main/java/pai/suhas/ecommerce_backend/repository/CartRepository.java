package pai.suhas.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pai.suhas.ecommerce_backend.entity.Cart;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>
{
    Optional<Cart> findByUserAndProduct(User user, Product product);
    List<Cart> findByUser(User user);
}
