package se.pensionsmyndigheten.library.book;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<Book> findAllByTitleContaining(String title) {
        return bookRepository.findAllByTitleContaining(title);
    }

    public List<Book> findAllByAuthorContaining(String author) {
        return bookRepository.findAllByAuthorContaining(author);
    }


    public List<Book> allOrderBy(OrderBy orderBy) {
        Stream<Book> bookStream = bookRepository.findAll().stream();
        return switch (orderBy) {
            case AUTHOR -> bookStream
                    .sorted(Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER))
                    .toList();
            case TITLE -> bookStream
                    .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                    .toList();
        };
    }

}
