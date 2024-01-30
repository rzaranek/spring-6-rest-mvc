package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-09.
 */
public interface CustomerService {
    List<CustomerDTO> listCustomers();
    Optional<CustomerDTO> getCustomerById(UUID id);
    CustomerDTO savedNewCustomer(CustomerDTO customer);

    void updateById(UUID customerId, CustomerDTO customer);

    void deleteById(UUID customerId);

    void patchCustomerById(UUID idCustomer, CustomerDTO customer);
}
