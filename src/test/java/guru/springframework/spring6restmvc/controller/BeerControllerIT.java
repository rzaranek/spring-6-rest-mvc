package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by robertZ on 2024-03-18.
 */
@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerController beerController;
    @Autowired
    BeerMapper beerMapper;

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteById() {
        UUID beerId = beerRepository.findAll().get(0).getId();

        ResponseEntity responseEntity = beerController.deleteById(beerId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beerId)).isEmpty();
    }

    @Test
    void testUpdateNotFoundFound() {
        assertThrows(NotFoundException.class, () -> {
           beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingBeer() {
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beerRepository.findAll().get(0));
        UUID beerId = beerDTO.getId();
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String newBeerName = "VeryStrongBeer";
        beerDTO.setBeerName(newBeerName);

        ResponseEntity responseEntity = beerController.updateById(beerId, beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beerId).get().getBeerName()).isEqualTo(newBeerName);
    }

    @Transactional
    @Rollback
    @Test
    void saveNewBeerTest() {
        BeerDTO newBeer = BeerDTO.builder()
                .beerName("Strong")
                .beerStyle(BeerStyle.PORTER)
                .price(BigDecimal.valueOf(14.5))
                .build();

        ResponseEntity responseEntity = beerController.handlePoste(newBeer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testBeerIsNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetBeerById() {
        //given
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());

        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testListBeers() {
        //given
        List<BeerDTO> beerDTOList = beerController.listBeers();

        assertThat(beerDTOList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOList = beerController.listBeers();

        assertThat(beerDTOList.size()).isEqualTo(0);
    }
}