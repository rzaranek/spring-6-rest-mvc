package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity handlerNotFound() {
        System.out.println("In Beer Exception Handler");
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = BEER_PATH_ID)
    BeerDTO getBeerById(@PathVariable(BEER_ID) UUID beerId) {

        log.debug("Get Beer By ID - in controller " + beerId);

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = BEER_PATH)
    List<BeerDTO> listBeers(@RequestParam(required = false) String beerName) {
        return beerService.listBeers(beerName);
    }

    @PatchMapping(value = BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable(BEER_ID) UUID beerId, @RequestBody BeerDTO beer) {

        if (beerService.patchBeerById(beerId, beer).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable(BEER_ID) UUID beerId) {
        if (!(beerService.deleteById(beerId)))
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable(BEER_ID) UUID beerId, @Validated @RequestBody BeerDTO beer) {

        if (beerService.updateBeerById(beerId, beer).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity handlePoste(@Validated @RequestBody BeerDTO beer) {

        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
}
