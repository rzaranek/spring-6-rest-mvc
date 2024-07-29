package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 25;

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {

        int queryPageNumber = pageNumber != null && pageNumber > 0 ? pageNumber : DEFAULT_PAGE;
        int queryPageSize = pageSize == null ? DEFAULT_SIZE : pageSize;

        if (queryPageSize > 1000) queryPageSize = 1000;

        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory,
                                   Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);

//        return beerPage
//                .stream()
//                .map(beerMapper::beerToBeerDto)
//                .toList();
    }

    private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle('%' + beerName + '%', beerStyle, pageable);
    }

    Page<Beer> listBeersByName(String beerName, org.springframework.data.domain.Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase('%' + beerName + '%', pageable);
    }

    Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
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
