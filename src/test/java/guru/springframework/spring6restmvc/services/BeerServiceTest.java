package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by robertZ on 2024-01-30.
 */

class BeerServiceTest {

    BeerService beerService = new BeerServiceImpl();

    @Test
    void getBeerById() {

        BeerDTO beer = beerService.listBeers(null, null, false, 1, 25).getContent().get(0);

        BeerDTO beerFound = beerService.getBeerById(beer.getId()).get();

        assertThat(beerFound).isEqualTo(beer);
    }

    @Test
    void getBeerByIdNotFound(){

        Optional beer = beerService.getBeerById(UUID.randomUUID());

        assertThat(beer).isEmpty();
    }
}