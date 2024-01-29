package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
public interface BeerService {

    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, Beer beer);
}
