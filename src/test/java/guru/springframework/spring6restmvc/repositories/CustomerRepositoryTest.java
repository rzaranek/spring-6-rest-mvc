package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by robertZ on 2024-01-31.
 */
@DataJpaTest
class CustomerRepositoryTest {

    public static final String CUSTOMER_NAME = "Apple Corp.";
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void createCustomerTest(){

        Customer creatdCustomer = customerRepository.save(Customer.builder()
                        .name(CUSTOMER_NAME)
                .build());

        assertThat(creatdCustomer).isNotNull();
        assertThat(creatdCustomer.getName()).isEqualTo(CUSTOMER_NAME);
    }

}