package hello;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query(
            value = "SELECT * FROM book WHERE book_name = ?1",
            nativeQuery = true)
    Book findByBookname(String bookname);

    Book findBookByBookName(String bookname);
    @Query(
            value = "SELECT * FROM book WHERE author_name = ?1 LIMIT 1",
            nativeQuery = true)
    Book findBookByAuthorName(String authorname);

    Iterable<Book> findBooksByAuthorName(String authorname);

    Book findBookByIsbn(String isbn);
}
