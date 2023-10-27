package se.pensionsmyndigheten.library.book;

import lombok.Getter;

@Getter
public class OrderByException
        extends RuntimeException {

    private final String orderBy;

    public OrderByException(final String orderBy) {
        this.orderBy = orderBy;
    }

}
