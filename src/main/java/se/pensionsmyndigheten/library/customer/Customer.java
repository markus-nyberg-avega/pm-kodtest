package se.pensionsmyndigheten.library.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import se.pensionsmyndigheten.library.book.Book;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID uuid;
    private String firstname;
    private String lastname;
    @OneToMany
    private List<Book> books;
}
