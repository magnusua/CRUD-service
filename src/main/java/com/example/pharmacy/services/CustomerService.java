package com.example.pharmacy.services;

import com.example.pharmacy.data.entity.Customer;
import com.example.pharmacy.data.repository.CustomerRepository;
import com.example.pharmacy.data.specifications.CustomerDataTableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> getCustomersForDatatable(String queryString, Pageable pageable) {

        CustomerDataTableFilter customerDataTableFilter = new CustomerDataTableFilter(queryString);

        return customerRepository.findAll(customerDataTableFilter, pageable);
    }
}
