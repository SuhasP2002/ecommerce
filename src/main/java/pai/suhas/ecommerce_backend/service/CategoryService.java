package pai.suhas.ecommerce_backend.service;

import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.CategoryResponse;
import pai.suhas.ecommerce_backend.dto.CreateCategoryRequest;
import pai.suhas.ecommerce_backend.entity.Category;
import pai.suhas.ecommerce_backend.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse createCategory(CreateCategoryRequest request)
    {
        if(categoryRepository.findByName(request.getName()).isPresent())
        {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(request.getName());

        Category savedCategory = categoryRepository.save(category);

        return mapToCategoryResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories()
    {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(this::mapToCategoryResponse)
                .toList();
    }

    private CategoryResponse mapToCategoryResponse(Category category)
    {
        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    }
}