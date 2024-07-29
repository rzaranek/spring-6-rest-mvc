package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {

        beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
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

        BeerDTO beer2 = BeerDTO.builder()
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

        BeerDTO beer3 = BeerDTO.builder()
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
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(beerMap.values()));
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return beerMap.get(id) == null ? Optional.empty() : Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {

        BeerDTO createBeer = BeerDTO.builder()
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
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {

        BeerDTO beerUpdated = beerMap.get(beerId);

        beerUpdated.setBeerName(beer.getBeerName());
        beerUpdated.setBeerStyle(beer.getBeerStyle());
        beerUpdated.setUpdateDate(LocalDateTime.now());
        beerUpdated.setPrice(beer.getPrice());
        beerUpdated.setUpc(beerUpdated.getUpc());
        beerUpdated.setQuantityOnHand(beerUpdated.getQuantityOnHand());

        return Optional.of(beerUpdated);
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        beerMap.remove(beerId);

        return true;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO updateteBeer = beerMap.get(beerId);

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

        return Optional.of(updateteBeer);
    }
}
