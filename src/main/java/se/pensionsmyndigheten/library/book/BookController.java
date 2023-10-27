package se.pensionsmyndigheten.library.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/book")
public class BookController {

  private final BookService bookService;

  public BookController(final BookService bookService) {
    this.bookService = bookService;
  }


  @GetMapping("/orderBy/{orderBy}")
  public List<BookDto> allOrderBy(@PathVariable String orderBy) {
    return OrderBy.of(orderBy)
                  .map(bookService::allOrderBy)
                  .map(bookList -> bookList.stream().map(BookDto::fromBook).toList())
                  .orElseThrow(() -> new OrderByException(orderBy));
  }

  @GetMapping("/author/{author}")
  public List<BookDto> byAuthor(@PathVariable String author) {
    return bookService.findAllByAuthorContaining(author)
                      .stream()
                      .map(BookDto::fromBook)
                      .toList();
  }


  @GetMapping("/title/{title}")
  public List<BookDto> byTitle(@PathVariable String title) {
    return bookService.findAllByTitleContaining(title)
                      .stream()
                      .map(BookDto::fromBook)
                      .toList();
  }

}
