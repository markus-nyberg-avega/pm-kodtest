package se.pensionsmyndigheten.library.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDto {
    private String isbn;
    private String author;
    private String title;

    static BookDto fromBook(Book book) {
        return new BookDto(book.getIsbn(), book.getAuthor(), book.getTitle());
    }
}
