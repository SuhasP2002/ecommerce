package pai.suhas.ecommerce_backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.RegisterRequest;
import pai.suhas.ecommerce_backend.entity.User;
import pai.suhas.ecommerce_backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody RegisterRequest request)
    {
        return userService.registerUser(request);
    }
    @GetMapping
    public List<User> getAllUser()
    {
        return userService.getAllUsers();
    }
}
