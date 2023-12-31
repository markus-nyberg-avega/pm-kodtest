package se.pensionsmyndigheten.library.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer not found.")
public class CustomerNotFoundException extends RuntimeException{
}
