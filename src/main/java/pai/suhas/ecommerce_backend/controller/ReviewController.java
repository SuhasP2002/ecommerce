package pai.suhas.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.AddReviewRequest;
import pai.suhas.ecommerce_backend.dto.ReviewResponse;
import pai.suhas.ecommerce_backend.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController
{
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService)
    {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponse addReview(@RequestBody AddReviewRequest request)
    {
        return reviewService.addReview(request);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponse> getReviews(@PathVariable Long productId)
    {
        return reviewService.getReviews(productId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId)
    {
        reviewService.deleteReview(reviewId);
    }
}