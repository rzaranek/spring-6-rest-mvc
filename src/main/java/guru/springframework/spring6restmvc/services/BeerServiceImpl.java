package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by robertZ on 2024-01-07.
 */
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {

        beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Hora")
                .beerStyle(BeerStyle.PILSNER)
                .upc("aa1")
                .quantityOnHand(22)
                .price(BigDecimal.valueOf(12.21))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now().plusDays(10))
                .build();

        beerMap.put(beer1.getId(), beer1);

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Hora")
                .beerStyle(BeerStyle.PILSNER)
                .upc("bb2")
                .quantityOnHand(47)
                .price(BigDecimal.valueOf(8.10))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now().plusDays(10))
                .build();

        beerMap.put(beer2.getId(), beer2);

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Żywiec")
                .beerStyle(BeerStyle.PILSNER)
                .upc("cc3")
                .quantityOnHand(5)
                .price(BigDecimal.valueOf(9.99))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now().plusDays(10))
                .build();

        beerMap.put(beer3.getId(), beer3);

    }

    @Override
    public List<Beer> listBeers(){
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<Beer> getBeerById(UUID id) {
        return beerMap.get(id) == null ? Optional.empty() : Optional.of(beerMap.get(id));
    }

    @Override
    public Beer saveNewBeer(Beer beer) {

        Beer createBeer = Beer.builder()
                .id(UUID.randomUUID())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(createBeer.getId(), createBeer);

        return createBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, Beer beer) {

        Beer beerUpdated = beerMap.get(beerId);

        beerUpdated.setBeerName(beer.getBeerName());
        beerUpdated.setBeerStyle(beer.getBeerStyle());
        beerUpdated.setUpdateDate(LocalDateTime.now());
        beerUpdated.setPrice(beer.getPrice());
        beerUpdated.setUpc(beerUpdated.getUpc());
        beerUpdated.setQuantityOnHand(beerUpdated.getQuantityOnHand());

    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, Beer beer) {
        Beer updateteBeer = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName()))
            updateteBeer.setBeerName(beer.getBeerName());
        if (beer.getBeerStyle() != null)
            updateteBeer.setBeerStyle(beer.getBeerStyle());
        if (beer.getPrice() != null)
            updateteBeer.setPrice(beer.getPrice());
        if (StringUtils.hasText(beer.getUpc()))
            updateteBeer.setUpc(beer.getUpc());
        if (beer.getQuantityOnHand() != null)
            updateteBeer.setQuantityOnHand(beer.getQuantityOnHand());
        updateteBeer.setUpdateDate(LocalDateTime.now());
    }
}
