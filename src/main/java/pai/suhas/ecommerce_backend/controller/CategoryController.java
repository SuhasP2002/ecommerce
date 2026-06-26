package pai.suhas.ecommerce_backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.CategoryResponse;
import pai.suhas.ecommerce_backend.dto.CreateCategoryRequest;
import pai.suhas.ecommerce_backend.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController
{
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponse createCategory(
            @Valid @RequestBody CreateCategoryRequest request)
    {
        return categoryService.createCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories()
    {
        return categoryService.getAllCategories();
    }
}