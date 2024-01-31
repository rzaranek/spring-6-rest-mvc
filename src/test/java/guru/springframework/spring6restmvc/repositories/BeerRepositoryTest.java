package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by robertZ on 2024-01-31.
 */
@DataJpaTest
class BeerRepositoryTest {

    public static final String MY_BEER_NAME = "My Beer 0%";
    @Autowired
    BeerRepository beerRepository;

    @Test
    void createBeerTest(){

        Beer createdBeer = beerRepository.save(Beer.builder()
                        .beerName(MY_BEER_NAME)
                .build());

        assertThat(createdBeer).isNotNull();
        assertThat(createdBeer.getBeerName()).isEqualTo(MY_BEER_NAME);

        Beer findBeer = beerRepository.findById(createdBeer.getId()).get();

        assertThat(findBeer.getBeerName()).isEqualTo(createdBeer.getBeerName());

    }

}