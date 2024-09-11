package ru.shestakov.Library.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.dto.BookDto;

import ru.shestakov.Library.service.BookService;


import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BookController {
 private final BookService bookService;


    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BookDto> showAll(){
        return bookService.findAll();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BookDto showOneById(@PathVariable Integer id){
        return bookService.findOne(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/isbn/{isbn}")
    public BookDto showByIsbn(@PathVariable String isbn){
        return bookService.findByIsbn(isbn);
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody BookDto bookDTO){
        bookService.save(bookDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id){
     bookService.delete(id);
}


    @PutMapping("/update/{id}")
    public void update(@PathVariable Integer id, @RequestBody BookDto bookDTO){
        bookService.updateBook(id, bookDTO);
    }

}

