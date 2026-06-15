package pai.suhas.ecommerce_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.RegisterRequest;
import pai.suhas.ecommerce_backend.entity.Role;
import pai.suhas.ecommerce_backend.entity.User;
import pai.suhas.ecommerce_backend.repository.UserRepository;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest request)
    {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Server decides role
        user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}
