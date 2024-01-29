package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by robertZ on 2024-01-09.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .name("Apple")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);

        Customer customer2 = Customer.builder()
                .name("Alphabet")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer2.getId(), customer2);

        Customer customer3 = Customer.builder()
                .name("Samsung")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer3.getId(), customer3);

        Customer customer4 = Customer.builder()
                .name("Sony")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer4.getId(), customer4);

    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        return (customerMap.get(id) == null) ? Optional.empty() : Optional.of(customerMap.get(id));
    }

    @Override
    public Customer savedNewCustomer(Customer customer) {

        Customer creadteCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(creadteCustomer.getId(), creadteCustomer);

        return creadteCustomer;
    }

    @Override
    public void updateById(UUID customerId, Customer customer) {
        Customer updatedCustomer = customerMap.get(customerId);

        updatedCustomer.setName(customer.getName());
        updatedCustomer.setLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public void deleteById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID idCustomer, Customer customer) {
        Customer savedCustomer = customerMap.get(idCustomer);

        if (StringUtils.hasText(customer.getName()))
            savedCustomer.setName(customer.getName());

        savedCustomer.setLastModifiedDate(LocalDateTime.now());
    }

}
