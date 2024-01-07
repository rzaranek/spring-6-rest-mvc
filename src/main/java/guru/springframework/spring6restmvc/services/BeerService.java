package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;

import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
public interface BeerService {
    Beer getBeerById(UUID id);
}
