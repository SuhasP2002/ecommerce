package pai.suhas.ecommerce_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController
{
    private final EmailService emailService;

    public EmailController(EmailService emailService)
    {
        this.emailService = emailService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> sendTestEmail(
            @RequestParam String to) throws Exception
    {
        emailService.sendOrderConfirmationEmail(
                to,
                "Suhas",
                101L,
                "29 June 2026",
                2499.0
        );

        return ResponseEntity.ok("HTML Email Sent Successfully");
    }
}