package ru.shestakov.book.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.book.dto.BookDto;
import ru.shestakov.book.dto.ResponseBookDto;

import ru.shestakov.book.entity.Status;
import ru.shestakov.book.service.BookService;


import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }



    @GetMapping("/books")
    public List<ResponseBookDto> showAll(){
        return bookService.findAll();
    }



    @GetMapping("book/{id}")
    public ResponseEntity<?> showOneById(@PathVariable Integer id){
        return new ResponseEntity<>(bookService.findOne(id),HttpStatus.OK);
    }



    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<?> showByIsbn(@PathVariable String isbn){
        return new ResponseEntity<>(bookService.findByIsbn(isbn), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody BookDto bookDto, @RequestHeader String Authorization){
       return new ResponseEntity<>(bookService.save(bookDto,Authorization),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
     bookService.delete(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?>  update(@PathVariable Integer id, @RequestBody BookDto bookDto){
    return new ResponseEntity<>(bookService.updateBook(id, bookDto),HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody Status status, @RequestHeader String Authorization){
        return new ResponseEntity<>(bookService.updateStatusById(id,status,Authorization),HttpStatus.OK);
    }

}

