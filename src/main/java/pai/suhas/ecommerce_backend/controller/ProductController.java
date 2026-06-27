package pai.suhas.ecommerce_backend.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest createProductRequest)
    {
        return productService.createProduct(createProductRequest);
    }

    @GetMapping
    public Page<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        return productService.getAllProduct(page,size,sortBy,direction);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductRequest request)
    {
        return productService.updateProduct(id,request);
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(@RequestParam String keyword)
    {
        return productService.searchProducts(keyword);
    }
}
