package edu.icet.ecom.notification.kafka.consumer;

import edu.icet.ecom.notification.entity.Notification;
import edu.icet.ecom.notification.kafka.order.OrderConfirmation;
import edu.icet.ecom.notification.kafka.payment.PaymentConfirmation;
import edu.icet.ecom.notification.repository.NotificationRepository;
import edu.icet.ecom.notification.service.EmailService;
import edu.icet.ecom.notification.util.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming payment success notification: {}", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationTime(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                .build());
        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessNotification(
                paymentConfirmation.customerEmail(),
                paymentConfirmation.orderReference(),
                customerName,
                paymentConfirmation.amount()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming order confirmation notification: {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .notificationTime(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                .build());

        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationNotification(
                orderConfirmation.customer().email(),
                orderConfirmation.orderReference(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.products()
        );
    }
}
