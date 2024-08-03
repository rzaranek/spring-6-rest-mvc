package guru.springframework.spring6restmvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerOrderRepository() {

        System.out.println(beerRepository.count());
        System.out.println(customerRepository.count());
        System.out.println(beerOrderRepository.count());
    }
}