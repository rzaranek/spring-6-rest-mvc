package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by robertZ on 2024-03-18.
 */
@SpringBootTest
class CustomerControllerIT {

    public static final String CUSTOMER_NAME = "Updated name customer";
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void patchByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updatePatchById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void patchById() {
        UUID customerId = customerRepository.findAll().get(0).getId();
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(CUSTOMER_NAME)
                .build();

        ResponseEntity responseEntity = customerController.updatePatchById(customerId, customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customerId).get().getName()).isEqualTo(CUSTOMER_NAME);
    }

    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.deleteCustomerById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {
        UUID customerId = customerRepository.findAll().get(0).getId();

        ResponseEntity responseEntity = customerController.deleteCustomerById(customerId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customerId)).isEmpty();
    }

    @Test
    void updateByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void updateById() {
        UUID customerId = customerRepository.findAll().get(0).getId();
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(CUSTOMER_NAME)
                .build();

        ResponseEntity responseEntity = customerController.updateCustomerById(customerId, customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customerId).get().getName()).isEqualTo(CUSTOMER_NAME);
    }

    @Transactional
    @Rollback
    @Test
    void createNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.createNewCustomer(customerDTO);
        String[] locationStr = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID idCustomer = UUID.fromString(locationStr[locationStr.length - 1]);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(customerRepository.findById(idCustomer)).isNotNull();
    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void customerNotFound() {
        assertThrows(NotFoundException.class, () -> {
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
    void testEmptyList() {
        customerRepository.deleteAll();

        List<CustomerDTO> customerDTOList = customerController.getCustomers();

        assertThat(customerDTOList).isEmpty();
    }
}