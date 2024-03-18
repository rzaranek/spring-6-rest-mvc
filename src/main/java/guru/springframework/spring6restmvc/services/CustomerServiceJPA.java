package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by robertZ on 2024-03-18.
 */
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return null;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public CustomerDTO savedNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteById(UUID customerId) {

    }

    @Override
    public void patchCustomerById(UUID idCustomer, CustomerDTO customer) {

    }
}
