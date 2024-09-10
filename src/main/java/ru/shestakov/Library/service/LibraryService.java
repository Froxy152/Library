package ru.shestakov.Library.service;


import org.springframework.stereotype.Service;
import ru.shestakov.Library.DTO.LibraryDTO;
import ru.shestakov.Library.entity.Book;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.repo.BookRepo;
import ru.shestakov.Library.repo.LibraryRepository;
import ru.shestakov.Library.util.BookNotFoundedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LibraryService {
    private final BookRepo bookRepo;
    private final LibraryRepository libraryRepository;


    public LibraryService(BookRepo bookRepo, LibraryRepository libraryRepository) {
        this.bookRepo = bookRepo;
        this.libraryRepository = libraryRepository;
    }

    public void save(Library library){
        library.setTaken_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        libraryRepository.save(library);
    }
    public List<Library> showAllFreeBooks(){
        return libraryRepository.findAll();
    }

    public void setStatusById(Integer id, String status){
        Book book = bookRepo.findById(id).orElseThrow(BookNotFoundedException::new);

        book.setStatus(status);
        if(status.equals("oc")){
            libraryRepository.deleteByBook(book.getId());
        }
        bookRepo.save(book);
    }
}
