package pl.sda.webstore.repositories;

import org.springframework.stereotype.Repository;
import pl.sda.webstore.domains.Customer;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private List<Customer> listOfCustomers = new ArrayList<>();

    public InMemoryCustomerRepository() {
        listOfCustomers.add(new Customer("1", "Arek", "Łódź", 3));
        listOfCustomers.add(new Customer("24", "Tomek", "Szczecin", 0));
        listOfCustomers.add(new Customer("485", "Anna", "Warszawa", 12));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return listOfCustomers;
    }
}
