package pai.suhas.ecommerce_backend.service;

import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.ProductResponse;
import pai.suhas.ecommerce_backend.exception.ProductNotFoundException;
import pai.suhas.ecommerce_backend.dto.CreateProductRequest;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService
{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public Product createProduct(CreateProductRequest request)
    {
        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }

    public List<ProductResponse> getAllProduct()
    {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse).toList();
    }

    public ProductResponse getProductById(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found " + id));

        return mapToProductResponse(product);
    }
    public Product updateProduct(Long id, CreateProductRequest createProductRequest)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ProductNotFoundException("Product not found " + id));

        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        product.setDescription(createProductRequest.getDescription());
        product.setStockQuantity(createProductRequest.getStockQuantity());
        product.setImageUrl(createProductRequest.getImageUrl());

        return productRepository.save(product);
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

        return response;
    }
}