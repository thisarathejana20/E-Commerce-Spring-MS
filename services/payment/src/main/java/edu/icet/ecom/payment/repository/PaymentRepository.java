package edu.icet.ecom.payment.repository;

import edu.icet.ecom.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
