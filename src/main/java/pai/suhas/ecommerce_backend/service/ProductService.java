package pai.suhas.ecommerce_backend.service;

import org.springframework.stereotype.Service;
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

    public List<Product> getAllProduct()
    {
        return productRepository.findAll();
    }

    public Product getProductById(Long id)
    {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }
    public Product updateProduct(Long id, CreateProductRequest createProductRequest)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Product not found"));

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
                        new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}