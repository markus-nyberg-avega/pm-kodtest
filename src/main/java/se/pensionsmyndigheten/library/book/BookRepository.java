package se.pensionsmyndigheten.library.book;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

  List<Book> findAllByTitleContaining(String title);
  List<Book> findAllByAuthorContaining(String author);
  List<Book> findAllByIsbn(String isbn);

}
