package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID id) {

        log.debug("BeerService create Beer by ID " + id);

        return Beer.builder()
                .id(id)
                .beerName("Hora")
                .beerStyle(BeerStyle.PILSNER)
                .upc("upc")
                .quantityOnHand(22)
                .price(BigDecimal.valueOf(12.21))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now().plusDays(10))
                .build();
    }
}
