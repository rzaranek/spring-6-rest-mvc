package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by robertZ on 2024-01-31.
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
