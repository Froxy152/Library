package ru.shestakov.Library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shestakov.Library.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {

    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByTitle(String title);
    boolean existsByIsbn(String isbn);
    boolean existsByTitle(String title);
    List<Book> findAllByStatus(String status);

}
