package pai.suhas.ecommerce_backend.dto;

import java.time.LocalDateTime;

public class OrderResponse
{
    private Long id;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public LocalDateTime getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate)
    {
        this.orderDate = orderDate;
    }
}