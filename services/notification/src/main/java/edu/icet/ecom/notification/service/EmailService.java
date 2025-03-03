package edu.icet.ecom.notification.service;

import edu.icet.ecom.notification.kafka.order.OrderConfirmation;
import edu.icet.ecom.notification.kafka.order.Product;
import edu.icet.ecom.notification.kafka.payment.PaymentConfirmation;
import edu.icet.ecom.notification.util.EmailTemplates;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderConfirmationNotification(
            String destinationEmail,
            String orderReference,
            String customerName,
            BigDecimal amount,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        mimeMessageHelper.setFrom("U9H5S@example.com");

        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplateName();

        HashMap<String , Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);

        mimeMessageHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationEmail);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error sending email", e);
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendPaymentSuccessNotification(
            String destinationEmail,
            String orderReference,
            String customerName,
            BigDecimal amount) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        mimeMessageHelper.setFrom("U9H5S@example.com");

        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplateName();

        HashMap<String , Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);

        mimeMessageHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationEmail);

            javaMailSender.send(mimeMessage);

            log.info("Email sent to {}", destinationEmail);
        } catch (MessagingException e) {
            log.warn("Failed to send email to {}", destinationEmail);
            throw new RuntimeException(e);
        }
    }
}
