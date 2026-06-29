package pai.suhas.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pai.suhas.ecommerce_backend.entity.Order;
import pai.suhas.ecommerce_backend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByUser(User user);

    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);
}