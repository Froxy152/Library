package ru.shestakov.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shestakov.book.entity.Book;


import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    boolean existsByTitle(String title);

}
