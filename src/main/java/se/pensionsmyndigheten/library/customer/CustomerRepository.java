package se.pensionsmyndigheten.library.customer;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findByUuid(UUID uuid);
}
