package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.boostrap.BootstrapData;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by robertZ on 2024-01-31.
 */
@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    public static final String MY_BEER_NAME = "My Super Beer 0%";
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerNameAndStyle(){

        List<Beer> beers = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%", BeerStyle.PILSNER);

        assertThat(beers).hasSize(15);
    }

    @Test
    void testGetBeerByBeerName(){

        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isEqualTo(336);
    }

    @Test
    void testGetBeerByStyle(){

        List<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.valueOf("PILSNER"));

        assertThat(list.size()).isEqualTo(1163);
    }

    @Test
    void createBeerTooLongNameTest() {

        assertThrows(ConstraintViolationException.class, () -> {

            Beer beer = Beer.builder()
                    .beerName("Beer 0123456789+0123456789+0123456789+0123456789+0123456789")
                    .beerStyle(BeerStyle.PORTER)
                    .upc("123123132")
                    .price(BigDecimal.valueOf(9.99))
                    .build();

            beerRepository.save(beer);
            beerRepository.flush();

        });
    }

    @Test
    void createBeerTest() {

        Beer createdBeer = beerRepository.save(Beer.builder()
                .beerName(MY_BEER_NAME)
                .beerStyle(BeerStyle.PORTER)
                .upc("123123132")
                .price(BigDecimal.valueOf(9.99))
                .build());

        beerRepository.flush();

        assertThat(createdBeer).isNotNull();
        assertThat(createdBeer.getBeerName()).isEqualTo(MY_BEER_NAME);

        Beer findBeer = beerRepository.findById(createdBeer.getId()).get();

        assertThat(findBeer.getBeerName()).isEqualTo(createdBeer.getBeerName());

    }

}