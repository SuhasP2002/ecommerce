package pai.suhas.ecommerce_backend.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService
{
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${mail.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender,
                        TemplateEngine templateEngine)
    {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendOrderConfirmationEmail(
            String to,
            String customerName,
            Long orderId,
            String orderDate,
            Double amount) throws Exception
    {
        Context context = new Context();

        context.setVariable("customerName", customerName);
        context.setVariable("orderId", orderId);
        context.setVariable("orderDate", orderDate);
        context.setVariable("amount", amount);

        String html =
                templateEngine.process(
                        "order-confirmation",
                        context);

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject("🎉 Order Confirmed - ePai");
        helper.setText(html, true);

        mailSender.send(message);
    }
}