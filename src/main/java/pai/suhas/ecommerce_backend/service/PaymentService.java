package pai.suhas.ecommerce_backend.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.PaymentResponse;
import pai.suhas.ecommerce_backend.dto.VerifyPaymentRequest;

@Service
public class PaymentService
{
    private final OrderService orderService;
    private final EmailService emailService;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public PaymentService(
            OrderService orderService,
            EmailService emailService)
    {
        this.orderService = orderService;
        this.emailService = emailService;
    }

    public PaymentResponse createPayment(Long orderId)
            throws Exception
    {
        pai.suhas.ecommerce_backend.entity.Order appOrder =
                orderService.getOrderById(orderId);

        RazorpayClient razorpayClient =
                new RazorpayClient(keyId, keySecret);

        JSONObject request = new JSONObject();

        request.put(
                "amount",
                (int) (appOrder.getTotalAmount() * 100));

        request.put("currency", "INR");

        request.put(
                "receipt",
                "receipt_" + appOrder.getId());

        Order razorpayOrder =
                razorpayClient.orders.create(request);

        appOrder.setRazorpayOrderId(
                razorpayOrder.get("id"));

        orderService.saveOrder(appOrder);

        PaymentResponse response =
                new PaymentResponse();

        response.setOrderId(
                razorpayOrder.get("id"));

        response.setAmount(
                appOrder.getTotalAmount());

        response.setKey(keyId);

        return response;
    }

    public boolean verifyPayment(
            VerifyPaymentRequest request)
            throws Exception
    {
        JSONObject options = new JSONObject();

        options.put(
                "razorpay_order_id",
                request.getRazorpayOrderId());

        options.put(
                "razorpay_payment_id",
                request.getRazorpayPaymentId());

        options.put(
                "razorpay_signature",
                request.getRazorpaySignature());

        boolean verified =
                Utils.verifyPaymentSignature(
                        options,
                        keySecret);

        if (verified)
        {
            pai.suhas.ecommerce_backend.entity.Order order =
                    orderService.getOrderByRazorpayOrderId(
                            request.getRazorpayOrderId());

            orderService.markOrderAsPaid(order.getId());

            emailService.sendOrderConfirmationEmail(
                    order.getUser().getEmail(),
                    order.getUser().getUsername(),
                    order.getId(),
                    order.getOrderDate().toString(),
                    order.getTotalAmount()
            );
        }

        return verified;
    }
}