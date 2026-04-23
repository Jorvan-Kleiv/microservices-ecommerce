package com.jdt.ecommerce.customer;

import com.jdt.ecommerce.exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request) {
        Customer customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(String id, @Valid CustomerRequest request) {
        repository.findById(id).ifPresentOrElse(entity -> {
            entity.setFirstName(request.firstName());
            entity.setLastName(request.lastName());
            entity.setEmail(request.email());
            entity.setAddress(request.address());
            repository.save(entity);
        }, () -> {throw new CustomerNotFoundException("Customer not found with id " + id);});
    }

    public List<CustomerResponse> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse).toList();
    }

    public Boolean existById(String customerId) {
        return repository.existsById(customerId);
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId).map(mapper::toResponse)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
