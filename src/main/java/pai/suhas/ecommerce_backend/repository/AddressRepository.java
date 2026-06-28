package pai.suhas.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pai.suhas.ecommerce_backend.entity.Address;
import pai.suhas.ecommerce_backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>
{
    List<Address> findByUser(User user);

    Optional<Address> findByUserAndDefaultAddressTrue(User user);
}