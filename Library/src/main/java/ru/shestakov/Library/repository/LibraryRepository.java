package ru.shestakov.Library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shestakov.Library.entity.Library;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library,Integer> {
 Optional<Library> findByBookId(Integer id);
 boolean existsByBookId(Integer id);
}
