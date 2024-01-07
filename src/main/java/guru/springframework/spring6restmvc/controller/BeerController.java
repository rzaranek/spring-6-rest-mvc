package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    Beer getBeerById(UUID id){

        log.debug("Get Beer By ID - in controller " + id);

        return beerService.getBeerById(id);
    }
}
