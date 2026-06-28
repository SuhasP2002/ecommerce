package pai.suhas.ecommerce_backend.dto;

import jakarta.validation.constraints.NotNull;

public class AddToWishlistRequest
{
    @NotNull(message = "Product ID is required")
    private Long productId;

    public AddToWishlistRequest()
    {
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }
}