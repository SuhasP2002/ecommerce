package pai.suhas.ecommerce_backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.ProductResponse;
import pai.suhas.ecommerce_backend.entity.Category;
import pai.suhas.ecommerce_backend.exception.ProductNotFoundException;
import pai.suhas.ecommerce_backend.dto.CreateProductRequest;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.repository.CategoryRepository;
import pai.suhas.ecommerce_backend.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService
{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request)
    {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return mapToProductResponse(savedProduct);
    }

    public Page<ProductResponse> getAllProduct(int page,int size,String sortBy,String direction)
    {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::mapToProductResponse);
    }

    public ProductResponse getProductById(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found " + id));

        return mapToProductResponse(product);
    }
    public ProductResponse updateProduct(Long id, CreateProductRequest createProductRequest)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found " + id));

        Category category = categoryRepository.findById(createProductRequest.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        product.setDescription(createProductRequest.getDescription());
        product.setStockQuantity(createProductRequest.getStockQuantity());
        product.setImageUrl(createProductRequest.getImageUrl());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return mapToProductResponse(updatedProduct);
    }

    public void deleteProduct(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ProductNotFoundException("Product not found " + id));
        productRepository.delete(product);
    }

    private ProductResponse mapToProductResponse(Product product)
    {
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setStockQuantity(product.getStockQuantity());

        if (product.getCategory() != null)
        {
            response.setCategoryName(product.getCategory().getName());
        }
        else
        {
            response.setCategoryName("Uncategorized");
        }

        return response;
    }
    public List<ProductResponse> searchProducts(String keyword)
    {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);

        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public List<ProductResponse> filterProducts(String category,Double minPrice,Double maxPrice)
    {
        List<Product> products;

        if (category != null && minPrice != null && maxPrice != null)
        {
            products = productRepository.findByCategoryAndPrice(category,minPrice,maxPrice);
        }
        else if (category != null)
        {
            products = productRepository.findByCategory_NameIgnoreCase(category);
        }
        else if (minPrice != null && maxPrice != null)
        {
            products = productRepository.findProductsByPriceRange(minPrice,maxPrice);
        }
        else
        {
            products = productRepository.findAll();
        }
        return products.stream().map(this::mapToProductResponse).toList();
    }
}