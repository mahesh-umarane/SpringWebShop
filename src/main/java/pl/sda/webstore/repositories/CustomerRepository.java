package pl.sda.webstore.repositories;

import pl.sda.webstore.domains.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAllCustomers();
}
