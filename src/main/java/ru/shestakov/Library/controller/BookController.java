package ru.shestakov.Library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.dto.ResponseBookDto;

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
    public List<ResponseBookDto> showAll(){
        return bookService.findAll();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<?> showOneById(@PathVariable Integer id){
        return new ResponseEntity<>(bookService.findOne(id),HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> showByIsbn(@PathVariable String isbn){
        return new ResponseEntity<>(bookService.findByIsbn(isbn), HttpStatus.OK);
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@RequestBody ResponseBookDto responseBookDTO){
       return new ResponseEntity<>(bookService.save(responseBookDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
     bookService.delete(id);
}


    @PutMapping("/update/{id}")
    public ResponseEntity<?>  update(@PathVariable Integer id, @RequestBody ResponseBookDto responseBookDTO){
    return new ResponseEntity<>(bookService.updateBook(id, responseBookDTO),HttpStatus.OK);
    }

}

