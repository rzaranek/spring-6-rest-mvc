package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by robertZ on 2024-01-30.
 */

class BeerServiceTest {

    BeerService beerService = new BeerServiceImpl();

    @Test
    void getBeerById() {

        Beer beer = beerService.listBeers().get(0);

        Beer beerFound = beerService.getBeerById(beer.getId()).get();

        assertThat(beerFound).isEqualTo(beer);
    }

    @Test
    void getBeerByIdNotFound(){

        Optional beer = beerService.getBeerById(UUID.randomUUID());

        assertThat(beer).isEmpty();
    }
}