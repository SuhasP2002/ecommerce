package pai.suhas.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoryRequest
{
    @NotBlank(message = "Category name is required")
    private String name;

    public CreateCategoryRequest()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}