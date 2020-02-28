package pl.sda.webstore.services;

import pl.sda.webstore.domains.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
}
