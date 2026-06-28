package pai.suhas.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.entity.Review;
import pai.suhas.ecommerce_backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>
{
    List<Review> findByProduct(Product product);

    Optional<Review> findByUserAndProduct(User user, Product product);

    @Query("""
            SELECT AVG(r.rating)
            FROM Review r
            WHERE r.product.id = :productId
            """)
    Double getAverageRating(Long productId);

    @Query("""
            SELECT COUNT(r)
            FROM Review r
            WHERE r.product.id = :productId
            """)
    Long getReviewCount(Long productId);
}