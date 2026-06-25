package pai.suhas.ecommerce_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pai.suhas.ecommerce_backend.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>
{
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
