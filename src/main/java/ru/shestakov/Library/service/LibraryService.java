package ru.shestakov.Library.service;


import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shestakov.Library.dto.LibraryDto;
import ru.shestakov.Library.entity.Book;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.entity.StatusEnum;
import ru.shestakov.Library.repo.BookRepository;
import ru.shestakov.Library.repo.LibraryRepository;
import ru.shestakov.Library.exceptions.BookNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public LibraryService(BookRepository bookRepository, LibraryRepository libraryRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
    }

    public void save(Library library){
        library.setTaken_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        libraryRepository.save(library);
    }
    public List<LibraryDto> showAllFreeBooks(){
        return libraryRepository.findAll().stream().map((this::convertToLibraryDto)).collect(Collectors.toList());
    }
    @Transactional
    public void setStatusById(Integer id, String status){
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        book.setStatus(status);
        if(status.equals(StatusEnum.OCCUPAED.toString())){
            libraryRepository.deleteByBook(book.getId());
        }
        bookRepository.save(book);
    }
    public LibraryDto convertToLibraryDto(Library library){
        return modelMapper.map(library, LibraryDto.class);
    }
}
