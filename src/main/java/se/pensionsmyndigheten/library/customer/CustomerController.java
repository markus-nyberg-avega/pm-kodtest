package se.pensionsmyndigheten.library.customer;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(final CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> findAll() {
        return StreamSupport.stream(service.findAll().spliterator(), false)
                            .toList();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable String id) {
        return service.findByUUid(parseUuid(id))
                      .orElseThrow(CustomerNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Customer insert(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable String uuid) {
        service.findByUUid(parseUuid(uuid))
               .ifPresent(service::delete);
    }

    @GetMapping("/{customerId}/reserve/{isbn}")
    public boolean reserve(@PathVariable String customerId, @PathVariable String isbn) {
        var cid = parseUuid(customerId);
        return service.reserve(cid, isbn);
    }


    @GetMapping("/{customerId}/unreserve/{isbn}")
    public boolean unreserve(@PathVariable String customerId, @PathVariable String isbn) {
        var cid = parseUuid(customerId);
        return service.unreserve(cid, isbn);
    }

    private UUID parseUuid(String uuid) throws UuidException {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new UuidException(uuid);
        }
    }


}
