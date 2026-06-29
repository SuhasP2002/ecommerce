package pai.suhas.ecommerce_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.PaymentResponse;
import pai.suhas.ecommerce_backend.dto.VerifyPaymentRequest;
import pai.suhas.ecommerce_backend.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController
{
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order/{orderId}")
    public PaymentResponse createOrder(
            @PathVariable Long orderId)
            throws Exception
    {
        return paymentService.createPayment(orderId);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyPaymentRequest request)
            throws Exception
    {
        boolean verified = paymentService.verifyPayment(request);

        if(verified)
        {
            return ResponseEntity.ok("Payment Verified");
        }

        return ResponseEntity.badRequest().body("Payment Failed");
    }
}