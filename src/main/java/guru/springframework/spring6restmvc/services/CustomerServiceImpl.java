package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by robertZ on 2024-01-09.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .name("Apple")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);

        CustomerDTO customer2 = CustomerDTO.builder()
                .name("Alphabet")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer2.getId(), customer2);

        CustomerDTO customer3 = CustomerDTO.builder()
                .name("Samsung")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer3.getId(), customer3);

        CustomerDTO customer4 = CustomerDTO.builder()
                .name("Sony")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer4.getId(), customer4);

    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return (customerMap.get(id) == null) ? Optional.empty() : Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO savedNewCustomer(CustomerDTO customer) {

        CustomerDTO creadteCustomer = CustomerDTO.builder()
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
    public Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customer) {
        CustomerDTO updatedCustomer = customerMap.get(customerId);

        updatedCustomer.setName(customer.getName());
        updatedCustomer.setLastModifiedDate(LocalDateTime.now());
        return Optional.of(updatedCustomer);
    }

    @Override
    public Boolean deleteById(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID idCustomer, CustomerDTO customer) {
        CustomerDTO savedCustomer = customerMap.get(idCustomer);

        if (StringUtils.hasText(customer.getName()))
            savedCustomer.setName(customer.getName());

        savedCustomer.setLastModifiedDate(LocalDateTime.now());
        return Optional.of(savedCustomer);
    }

}
