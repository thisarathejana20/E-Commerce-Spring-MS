package edu.icet.ecom.notification.repository;

import edu.icet.ecom.notification.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String > {
}
