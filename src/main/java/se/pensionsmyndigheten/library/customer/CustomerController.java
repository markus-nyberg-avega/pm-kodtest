package se.pensionsmyndigheten.library.customer;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<CustomerDto> findAll() {
        return StreamSupport.stream(service.findAll().spliterator(), false)
                            .map(CustomerDto::fromCustomer)
                            .toList();
    }

    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable String id) {
        return service.findByUUid(parseUuid(id))
                      .map(CustomerDto::fromCustomer)
                      .orElseThrow(CustomerNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerDto insert(@RequestBody CustomerDto customerDto) {
        Customer customer = service.save(customerDto.toCustomer());
        return CustomerDto.fromCustomer(customer);
    }

    @PutMapping
    public CustomerDto update(@RequestBody CustomerDto customerDto) {
        var customer = service.findByUUid(customerDto.getUuid());
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable String uuid) {
        service.findByUUid(parseUuid(uuid))
               .ifPresent(service::delete);
    }

    @GetMapping("/{customerId}/borrow/{isbn}")
    public boolean borrowBook(@PathVariable String customerId, @PathVariable String isbn) {
        var cid = parseUuid(customerId);
        return service.borrow(cid, isbn);
    }


    @GetMapping("/{customerId}/return/{isbn}")
    public boolean returnBook(@PathVariable String customerId, @PathVariable String isbn) {
        var cid = parseUuid(customerId);
        return service.returnBook(cid, isbn);
    }

    private UUID parseUuid(String uuid) throws UuidException {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new UuidException(uuid);
        }
    }


}
