package ru.shestakov.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.dto.LibraryDto;
import ru.shestakov.Library.service.LibraryService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<LibraryDto> getFreeListBooks(){
        return libraryService.showAllFreeBooks();
    }

    @PatchMapping("/setOc/{id}")
    public void setOc(@PathVariable Integer id, @RequestParam String status,@RequestHeader String Authorization
                      ){
        libraryService.setStatusById(id,status, Authorization);
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestParam Integer id, @RequestHeader String Authorization
    ){
        return new ResponseEntity<>(libraryService.save(id), HttpStatus.CREATED);
    }


}
