package ru.shestakov.Library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shestakov.Library.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByTitle(String title);
    boolean existsByIsbn(String isbn);
    boolean existsByTitle(String title);

}
