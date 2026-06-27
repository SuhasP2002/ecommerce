package pai.suhas.ecommerce_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pai.suhas.ecommerce_backend.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategory_NameIgnoreCase(String categoryName);

    @Query("""
           SELECT p
           FROM Product p
           WHERE p.price BETWEEN :minPrice AND :maxPrice
           """)
    List<Product> findProductsByPriceRange(@Param("minPrice") Double minPrice,@Param("maxPrice") Double maxPrice);

    @Query("""
           SELECT p
           FROM Product p
           WHERE LOWER(p.category.name) = LOWER(:category)
           AND p.price BETWEEN :minPrice AND :maxPrice
           """)
    List<Product> findByCategoryAndPrice(@Param("category") String category,@Param("minPrice") Double minPrice,@Param("maxPrice") Double maxPrice);
}