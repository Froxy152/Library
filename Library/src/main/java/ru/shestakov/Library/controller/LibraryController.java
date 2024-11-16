package ru.shestakov.Library.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.dto.LibraryDto;
import ru.shestakov.Library.service.LibraryService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/library")
@Tag(name="Учет свободных книг", description="Происходит учет свободных книг")
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    @Operation(
            summary = "Получение списка всех доступных книг", description = "Позволяет получить список книг"
    )
    @GetMapping("/books")
    public List<LibraryDto> getFreeListBooks(){
        return libraryService.showAllFreeBooks();
    }
    @GetMapping
    public ResponseEntity<?> healthCheck(@RequestHeader String Authorization){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Hidden
    @PostMapping("/add")
    public ResponseEntity<LibraryDto> save(@RequestParam Integer id, @RequestHeader String Authorization){
        return new ResponseEntity<>(libraryService.save(id), HttpStatus.CREATED);
    }
    @Hidden
    @DeleteMapping
    public void deleteByStatus(@RequestParam Integer id, @RequestHeader String Authorization){
        libraryService.deleteByStatus(id);
    }

}
