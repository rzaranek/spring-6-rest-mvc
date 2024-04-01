package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by robertZ on 2024-03-18.
 */
@SpringBootTest
class BeerControllerIT {

    public static final BigDecimal PRICE = BigDecimal.valueOf(6.32);
    public static final String BEER_NAME = "NEW Beer";
    public static final int QUANTITY_ON_HAND = 22;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerController beerController;
    @Autowired
    BeerMapper beerMapper;
    @Autowired
    ObjectMapper mapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testPatchBeerBadName() throws Exception {

        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Beer Name Too Long 0123456789+0123456789+0123456789+0123456789+0123456789");

        MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
//                .andExpect((ResultMatcher) jsonPath("$.length()", is(1)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

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

    @Transactional
    @Rollback
    @Test
    void patchBeerById() {
        Beer beer = beerRepository.findAll().get(0);
        beer.setBeerStyle(BeerStyle.LAGER);
        beer.setPrice(PRICE);
        beer.setBeerName(BEER_NAME);
        beer.setQuantityOnHand(QUANTITY_ON_HAND);

        ResponseEntity responseEntity = beerController.updateBeerPatchById(beer.getId(), beerMapper.beerToBeerDto(beer));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer savedBeer = beerRepository.findById(beer.getId()).get();

        assertThat(savedBeer.getBeerName()).isEqualTo(BEER_NAME);
        assertThat(savedBeer.getBeerStyle()).isEqualTo(BeerStyle.LAGER);
        assertThat(savedBeer.getPrice()).isEqualTo(PRICE);
        assertThat(savedBeer.getQuantityOnHand()).isEqualTo(QUANTITY_ON_HAND);

    }

    @Test
    void pathcBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
            beerController.updateBeerPatchById(UUID.randomUUID(), BeerDTO.builder().build()));
    }
}