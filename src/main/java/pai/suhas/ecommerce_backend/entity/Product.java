package pai.suhas.ecommerce_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private String description;

    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String imageUrl;

    public Product()
    {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getStockQuantity()
    {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity)
    {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}