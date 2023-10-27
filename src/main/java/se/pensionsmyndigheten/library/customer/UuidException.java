package se.pensionsmyndigheten.library.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UuidException extends RuntimeException {

    private final String uuid;
}
