package edu.icet.ecom.customer.repository;

import edu.icet.ecom.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
