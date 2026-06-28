package pai.suhas.ecommerce_backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.AddReviewRequest;
import pai.suhas.ecommerce_backend.dto.ProductRatingResponse;
import pai.suhas.ecommerce_backend.dto.ReviewResponse;
import pai.suhas.ecommerce_backend.entity.Product;
import pai.suhas.ecommerce_backend.entity.Review;
import pai.suhas.ecommerce_backend.entity.User;
import pai.suhas.ecommerce_backend.repository.ProductRepository;
import pai.suhas.ecommerce_backend.repository.ReviewRepository;
import pai.suhas.ecommerce_backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService
{
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            ProductRepository productRepository,
            UserRepository userRepository)
    {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ReviewResponse addReview(AddReviewRequest request)
    {
        User user = getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<Review> existingReview =
                reviewRepository.findByUserAndProduct(user, product);

        if(existingReview.isPresent())
        {
            throw new RuntimeException("You have already reviewed this product.");
        }

        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setReviewDate(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        return mapToResponse(savedReview);
    }

    public List<ReviewResponse> getReviews(Long productId)
    {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return reviewRepository.findByProduct(product)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deleteReview(Long reviewId)
    {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewRepository.delete(review);
    }

    private ReviewResponse mapToResponse(Review review)
    {
        ReviewResponse response = new ReviewResponse();

        response.setId(review.getId());
        response.setUserName(review.getUser().getEmail());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setReviewDate(review.getReviewDate());

        return response;
    }

    private User getCurrentUser()
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ProductRatingResponse getProductRating(Long productId)
    {
        Double averageRating = reviewRepository.getAverageRating(productId);
        Long totalReviews = reviewRepository.getReviewCount(productId);

        ProductRatingResponse response = new ProductRatingResponse();

        response.setAverageRating(
                averageRating == null ? 0.0 : averageRating);

        response.setTotalReviews(totalReviews);

        return response;
    }
}