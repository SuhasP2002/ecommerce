package pai.suhas.ecommerce_backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.CreateProductRequest;
import pai.suhas.ecommerce_backend.dto.ProductResponse;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController
{
    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody CreateProductRequest createProductRequest)
    {
        return productService.createProduct(createProductRequest);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts()
    {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductRequest request)
    {
        return productService.updateProduct(id,request);
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

}
