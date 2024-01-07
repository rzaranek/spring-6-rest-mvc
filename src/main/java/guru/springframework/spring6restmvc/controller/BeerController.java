package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {

    private final BeerService beerService;

    @RequestMapping("/api/v1/beer")
    List<Beer> listBeers(){
        return beerService.listBeers();
    }

    Beer getBeerById(UUID id){

        log.debug("Get Beer By ID - in controller " + id);

        return beerService.getBeerById(id);
    }
}
