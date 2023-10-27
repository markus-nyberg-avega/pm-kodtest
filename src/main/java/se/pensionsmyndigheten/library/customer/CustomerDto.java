package se.pensionsmyndigheten.library.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private UUID uuid;
    private String firstname;
    private String lastname;

    Customer toCustomer() {
        return new Customer(null, this.getUuid(), this.getFirstname(), this.getLastname(), List.of());
    }

    static CustomerDto fromCustomer(Customer customer) {
        return new CustomerDto(customer.getUuid(), customer.getFirstname(), customer.getLastname());
    }
}
