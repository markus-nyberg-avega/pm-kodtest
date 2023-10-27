package se.pensionsmyndigheten.library.customer;

import org.springframework.stereotype.Service;
import se.pensionsmyndigheten.library.book.Book;
import se.pensionsmyndigheten.library.book.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;


    public CustomerService(
            final CustomerRepository customerRepository,
            final BookRepository bookRepository
    ) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findByUUid(UUID uuid) {
        Customer customer = customerRepository.findByUuid(uuid);
        return Optional.ofNullable(customer);
    }

    public boolean borrow(UUID customerID, String isbn) {
        return findByUUid(customerID)
                .map(customer -> borrow(customer, isbn))
                .orElse(false);
    }

    private boolean borrow(
            final Customer customer,
            final String isbn
    ) {
        List<Book> books = bookRepository.findAllByIsbn(isbn);
        if (books.isEmpty()
                || hasAlreadyBeenReserve(customer, books)
                || hasReachedMaxNumberOfBorrowedBooks(customer)) {
            return false;
        }
        customer.getBooks().addAll(books);
        customerRepository.save(customer);
        return true;
    }

    private static boolean hasReachedMaxNumberOfBorrowedBooks(final Customer customer) {
        return customer.getBooks().size() >= 2;
    }

    private static boolean hasAlreadyBeenReserve(
            final Customer customer,
            final List<Book> books
    ) {
        return new HashSet<>(customer.getBooks()).containsAll(books);
    }

    public boolean returnBook(UUID customerID, String isbn) {
        return findByUUid(customerID)
                .map(customer -> returnBook(customer, isbn))
                .orElse(false);
    }

    private boolean returnBook(
            final Customer customer,
            final String isbn
    ) {
        List<Book> books = bookRepository.findAllByIsbn(isbn);
        if (books.isEmpty() || hasNotBeenReserve(customer, books)) {
            return false;
        }
        customer.getBooks().removeAll(books);
        customerRepository.save(customer);
        return true;
    }

    private static boolean hasNotBeenReserve(
            final Customer customer,
            final List<Book> books
    ) {
        return !new HashSet<>(customer.getBooks()).containsAll(books);
    }
}
