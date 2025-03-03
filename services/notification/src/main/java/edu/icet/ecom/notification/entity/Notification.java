package edu.icet.ecom.notification.entity;

import edu.icet.ecom.notification.kafka.order.OrderConfirmation;
import edu.icet.ecom.notification.kafka.payment.PaymentConfirmation;
import edu.icet.ecom.notification.util.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Notification {
    @Id
    private String id;
    private NotificationType notificationType;
    private LocalDateTime notificationTime;
    private PaymentConfirmation paymentConfirmation;
    private OrderConfirmation orderConfirmation;
}
