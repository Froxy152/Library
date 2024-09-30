package ru.shestakov.book.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.book.dto.ResponseBookDto;

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
    public ResponseEntity<?> save(@RequestBody ResponseBookDto responseBookDTO, @RequestHeader String Authorization
    ){
       return new ResponseEntity<>(bookService.save(responseBookDTO,Authorization
       ),HttpStatus.CREATED);
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
    @PatchMapping("/update/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam String status){
        return  new ResponseEntity<>(bookService.updateStatusById(id,status),HttpStatus.OK);
    }


}

