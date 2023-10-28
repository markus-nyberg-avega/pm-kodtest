package se.pensionsmyndigheten.library.book;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BookRepository extends ListCrudRepository<Book, Integer> {

  List<Book> findAllByTitleContaining(String title);
  List<Book> findAllByAuthorContaining(String author);
  List<Book> findAllByIsbn(String isbn);

}
