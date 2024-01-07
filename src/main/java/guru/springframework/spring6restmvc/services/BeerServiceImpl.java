package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStryle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeer(UUID id) {
        return Beer.builder()
                .id(id)
                .beerName("Hora")
                .beerStryle(BeerStryle.PILSNER)
                .upc("upc")
                .quantityOnHand(22)
                .price(BigDecimal.valueOf(12.21))
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now().plusDays(10))
                .build();
    }
}
