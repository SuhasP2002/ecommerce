package pai.suhas.ecommerce_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pai.suhas.ecommerce_backend.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>
{

}
