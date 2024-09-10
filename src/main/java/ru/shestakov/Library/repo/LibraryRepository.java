package ru.shestakov.Library.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shestakov.Library.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library,Integer> {
 void deleteByBook(Integer id);
}
