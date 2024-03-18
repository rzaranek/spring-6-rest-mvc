package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by robertZ on 2024-03-18.
 */
@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;
    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void customerNotFound(){
        assertThrows(NotFoundException.class, () ->{
           customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void getCustomers() {
        List<CustomerDTO> customerDTOList = customerController.getCustomers();

        assertThat(customerDTOList).hasSize(4);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyList(){
        customerRepository.deleteAll();

        List<CustomerDTO> customerDTOList = customerController.getCustomers();

        assertThat(customerDTOList).isEmpty();
    }
}