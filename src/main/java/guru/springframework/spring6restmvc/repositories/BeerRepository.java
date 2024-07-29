package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by robertZ on 2024-01-31.
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {


    Page<Beer> findAll(Pageable pageable);
    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(@NotNull String beerName, Pageable pageable);
    Page<Beer> findAllByBeerStyle(@NotNull BeerStyle beerStyle, Pageable pageable);
    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(@NotNull String beerName, @NotNull BeerStyle beerStyle, Pageable pageable);
}
