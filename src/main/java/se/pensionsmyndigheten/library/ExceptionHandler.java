package se.pensionsmyndigheten.library;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import se.pensionsmyndigheten.library.book.OrderByException;
import se.pensionsmyndigheten.library.customer.UuidException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(OrderByException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleOrderByException(final OrderByException e) {
        log.info("OrderBy can not be parsed, OrderByException caught", e);
        return ResponseEntity
                .badRequest()
                .body("Unsupported order by value, " + e.getOrderBy());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UuidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUuidException(final UuidException e) {
        log.info("UUID can not be parsed, UuidException caught", e);
        return ResponseEntity
                .badRequest()
                .body("Invalid UUID, " + e.getUuid());
    }
}
