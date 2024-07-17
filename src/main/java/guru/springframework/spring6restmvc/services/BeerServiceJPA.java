package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by robertZ on 2024-03-18.
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers(String beerName) {

        List<Beer> beerList;

        if (StringUtils.hasText(beerName)) {
            beerList = listBeersByName(beerName);
        } else {
            beerList = beerRepository.findAll();
        }

        return beerList
                .stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    List<Beer> listBeersByName(String beerName) {
        return new ArrayList<>();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundBeer.setBeerName(beerDTO.getBeerName());
            foundBeer.setUpc(beerDTO.getUpc());
            foundBeer.setPrice(beerDTO.getPrice());

            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        } else
            return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO) {

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(beer ->
                {
                    if (StringUtils.hasText(beerDTO.getBeerName()))
                        beer.setBeerName(beerDTO.getBeerName());
                    if (beerDTO.getBeerStyle() != null)
                        beer.setBeerStyle(beerDTO.getBeerStyle());
                    if (beerDTO.getPrice() != null)
                        beer.setPrice(beerDTO.getPrice());
                    if (beerDTO.getQuantityOnHand() != null)
                        beer.setQuantityOnHand(beerDTO.getQuantityOnHand());

                    atomicReference.set(Optional.of(beerMapper
                            .beerToBeerDto(beerRepository.save(beer))));
                },
                () ->
                        atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
}
