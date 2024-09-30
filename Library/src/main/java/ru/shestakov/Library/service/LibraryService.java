package ru.shestakov.Library.service;

import org.springframework.stereotype.Service;
import ru.shestakov.Library.dto.LibraryDto;
import ru.shestakov.Library.entity.Library;

import ru.shestakov.Library.feign.BookServiceProxy;
import ru.shestakov.Library.mapper.LibraryMapper;

import ru.shestakov.Library.repository.LibraryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;
    private final BookServiceProxy bookServiceProxy;

    public LibraryService(LibraryRepository libraryRepository, LibraryMapper libraryMapper, BookServiceProxy bookServiceProxy) {

        this.libraryRepository = libraryRepository;

        this.libraryMapper = libraryMapper;
        this.bookServiceProxy = bookServiceProxy;
    }
    public void setStatusById(Integer id,String status,String token){
       if(status.equals("OCCUPIED")) {
           libraryRepository.deleteByBook(id);
       }
       bookServiceProxy.updateStatus(id,status, token);
    }

    public LibraryDto save(Integer id){
        Library library = new Library();
        library.setBook(id);
        library.setTaken_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:HH:mm:ss")));
        library.setReturn_at(LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("dd:HH:mm:ss")));
        return libraryMapper.convertToLibraryDto(libraryRepository.save(library));
    }
    public List<LibraryDto> showAllFreeBooks(){
        return libraryRepository.findAll().stream().map((libraryMapper::convertToLibraryDto)).collect(Collectors.toList());
    }


}
