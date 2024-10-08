package ru.shestakov.Library.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.shestakov.Library.dto.LibraryDto;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.exceptions.BookAlReadyExistsInLibraryException;
import ru.shestakov.Library.mapper.LibraryMapper;
import ru.shestakov.Library.repository.LibraryRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;

    public LibraryService(LibraryRepository libraryRepository, LibraryMapper libraryMapper) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
    }

    public LibraryDto save(Integer id){
        if(libraryRepository.existsByBookId(id)){
            throw new BookAlReadyExistsInLibraryException();
        }
        Library library = new Library();
        library.setBookId(id);
        library.setTaken_at(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        library.setReturn_at(LocalDateTime.now().plusDays(14).toEpochSecond(ZoneOffset.UTC));
        return libraryMapper.convertToLibraryDto(libraryRepository.save(library));
    }
    public List<LibraryDto> showAllFreeBooks(){
        return libraryRepository.findAll().stream().map((libraryMapper::convertToLibraryDto)).collect(Collectors.toList());
    }
    @Transactional
    public void deleteByStatus(Integer id){
       libraryRepository.delete(libraryRepository.findByBookId(id).orElseThrow(RuntimeException::new));
    }

}
