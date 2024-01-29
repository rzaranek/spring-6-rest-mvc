package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_ID = "beerId";
    public static final String BEER_PATH_ID = "/api/v1/beer/{" + BEER_ID + "}";

    private final BeerService beerService;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handlerNotFound(){
        System.out.println("In Beer Exception Handler");
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = BEER_PATH_ID)
    Beer getBeerById(@PathVariable(BEER_ID) UUID beerId) {

        log.debug("Get Beer By ID - in controller " + beerId);

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = BEER_PATH)
    List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @PatchMapping(value = BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable(BEER_ID) UUID beerId, @RequestBody Beer beer) {

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable(BEER_ID) UUID beerId) {
        beerService.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable(BEER_ID) UUID beerId, @RequestBody Beer beer) {

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PostMapping(BEER_PATH)
    public ResponseEntity handlePoste(@RequestBody Beer beer) {

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
}
