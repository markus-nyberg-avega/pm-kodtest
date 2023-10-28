package se.pensionsmyndigheten.library.book;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, orderBy.type).ignoreCase();
        return bookRepository.findAll(Sort.by(order));
    }

}
