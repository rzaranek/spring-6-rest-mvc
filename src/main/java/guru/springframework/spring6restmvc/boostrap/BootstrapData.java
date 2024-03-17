package guru.springframework.spring6restmvc.boostrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by robertZ on 2024-03-17.
 */
@Component
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {

        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Hora")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("aa1")
                    .quantityOnHand(22)
                    .price(BigDecimal.valueOf(12.21))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now().plusDays(10))
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Hora")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("bb2")
                    .quantityOnHand(47)
                    .price(BigDecimal.valueOf(8.10))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now().plusDays(10))
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Żywiec")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("cc3")
                    .quantityOnHand(5)
                    .price(BigDecimal.valueOf(9.99))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now().plusDays(10))
                    .build();

            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {

        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Apple")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Alphabet")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Samsung")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer4 = Customer.builder()
                    .name("Sony")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4));
        }
    }
}
