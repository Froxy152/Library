package ru.shestakov.book.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Сервис для работы с книгами", description="Происходит взаимодействия с книгами")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }


    @Operation(
            summary = "Получение всех доступных книг", description = "Позволяет получить список всех доступных книг "
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/books")
    public List<ResponseBookDto> showAll(){
        return bookService.findAll();
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение определенной книги", description = "Позволяет получить книгу по ее id"
    )
    @GetMapping("book/{id}")
    public ResponseEntity<ResponseBookDto> showOneById(@PathVariable Integer id){
        return new ResponseEntity<>(bookService.findOne(id),HttpStatus.OK);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение определенной книги", description = "Позволяет получить книгу по ее isbn"
    )
    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<ResponseBookDto> showByIsbn(@PathVariable String isbn){
        return new ResponseEntity<>(bookService.findByIsbn(isbn), HttpStatus.OK);
    }
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление книги в базу данных", description = "Позволяет создать книгу"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseBookDto> save(@RequestBody BookDto bookDto, @RequestHeader String Authorization){
       return new ResponseEntity<>(bookService.save(bookDto,Authorization),HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление книги", description = "Позволяет удалить книгу"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
     bookService.delete(id);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Изменение данных книги", description = "Позволяет изменять книгу"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseBookDto>  update(@PathVariable Integer id, @RequestBody BookDto bookDto){
    return new ResponseEntity<>(bookService.updateBook(id, bookDto),HttpStatus.OK);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "изменение статуса в книги ", description = "Позволяет изменить статус книги (Status = FREE или OCCUPIED)"
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseBookDto> updateStatus(@PathVariable Integer id, @RequestBody Status status, @RequestHeader String Authorization){
        return new ResponseEntity<>(bookService.updateStatusById(id,status,Authorization),HttpStatus.OK);
    }

}

