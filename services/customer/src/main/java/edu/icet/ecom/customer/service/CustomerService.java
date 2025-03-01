package edu.icet.ecom.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.ecom.customer.dto.CustomerRequest;
import edu.icet.ecom.customer.dto.CustomerResponse;
import edu.icet.ecom.customer.repository.CustomerRepository;
import edu.icet.ecom.customer.util.customException.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public String createCustomer(CustomerRequest customer) {
        return customerRepository.save(
                objectMapper.convertValue(customer,
                        edu.icet.ecom.customer.entity.Customer.class)
        ).getId();
    }

    public String updateCustomer(String id, CustomerRequest customer) {
        var customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Customer with id %s not found", id)
                ));

        mergeCustomer(customerEntity, customer);
        return customerRepository.save(customerEntity).getId();
    }

    private void mergeCustomer(edu.icet.ecom.customer.entity.Customer customerEntity,
                               CustomerRequest customer) {
        if (StringUtils.isNotBlank(customer.firstName())) {
            customerEntity.setFirstName(customer.firstName());
        }
        if (StringUtils.isNotBlank(customer.lastName())) {
            customerEntity.setLastName(customer.lastName());
        }
        if (StringUtils.isNotBlank(customer.email())) {
            customerEntity.setEmail(customer.email());
        }
        if (customer.address() != null) {
            customerEntity.setAddress(customer.address());
        }
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().
                stream()
                .map(customer ->
                        objectMapper.
                                convertValue(customer,
                                        CustomerResponse.class))
                .toList();
    }

    public Boolean customerExists(String id) {
        return customerRepository.existsById(id);
    }

    public CustomerResponse findById(String id) {
        return customerRepository.findById(id)
                .map(customer ->
                        objectMapper
                                .convertValue(customer,
                                        CustomerResponse.class))
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                String.format("Customer with id %s not found", id)
                        ));
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
