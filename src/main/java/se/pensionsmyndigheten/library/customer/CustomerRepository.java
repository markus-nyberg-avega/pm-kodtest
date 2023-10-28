package se.pensionsmyndigheten.library.customer;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface CustomerRepository extends ListCrudRepository<Customer, Integer> {

    Customer findByUuid(UUID uuid);
}
