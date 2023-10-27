package se.pensionsmyndigheten.library.book;

import java.util.Arrays;
import java.util.Optional;

public enum OrderBy {
    TITLE("title"),
    AUTHOR("author");

    final String type;

    OrderBy(final String type) {
        this.type = type;
    }

    public static Optional<OrderBy> of(String type) {
        return Arrays.stream(values())
                     .filter(v -> v.type.equalsIgnoreCase(type))
                     .findAny();
    }
}
