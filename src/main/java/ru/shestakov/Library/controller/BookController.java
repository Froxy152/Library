package ru.shestakov.Library.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.DTO.BookDTO;
import ru.shestakov.Library.entity.Book;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.service.BookService;
import ru.shestakov.Library.service.LibraryService;
import ru.shestakov.Library.util.BookAllReadyExistsException;
import ru.shestakov.Library.util.BookErrorResponse;
import ru.shestakov.Library.util.BookNotFoundedException;

import javax.security.sasl.SaslServer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
@Controller
public class BookController {
 private final BookService bookService;
 private final ModelMapper modelMapper;

 @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper){
     this.bookService = bookService;
     this.modelMapper = modelMapper;

 }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BookDTO> showAll(){return (bookService.findAll().stream().map(this::convertToBookDto).collect(Collectors.toList()));}


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BookDTO showOneById(@PathVariable Integer id){return convertToBookDto(bookService.findOne(id));}


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/isbn/{isbn}")
    public BookDTO showByIsbn(@PathVariable String isbn){ return convertToBookDto(bookService.findByIsbn(isbn));}


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody BookDTO bookDTO){
     bookService.save(convertToBook(bookDTO));

 }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id){
     bookService.delete(id);
}


    @PutMapping("/update/{id}")
    public void update(@PathVariable Integer id, @RequestBody BookDTO bookDTO){
     bookService.updateBook(id, convertToBook(bookDTO));
}


    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundedException e){
     BookErrorResponse response = new BookErrorResponse(
             "Book wasn't found!",
             System.currentTimeMillis()
     );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleAllReadyExistsException(BookAllReadyExistsException e){
     BookErrorResponse response = new BookErrorResponse(
             "This is Book all ready exists",
             System.currentTimeMillis()
     );
     return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }


    public BookDTO convertToBookDto(Book book){
        return modelMapper.map(book,BookDTO.class);
    }

    public Book convertToBook(BookDTO bookDTO){
        return modelMapper.map(bookDTO,Book.class);
    }

}

