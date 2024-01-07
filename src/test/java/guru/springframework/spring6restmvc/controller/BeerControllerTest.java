package guru.springframework.spring6restmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
@SpringBootTest
class BeerControllerTest {

    @Autowired
    BeerController beerController;

    @Test
    void viewResultOfCreateBeerPOJO() {
        System.out.println(beerController.getBeerById(UUID.randomUUID()));
    }
}