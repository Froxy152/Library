package ru.shestakov.Library.controller;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PatchMapping("/set/{id}")
    public void setOc(@PathVariable Integer id){
        libraryService.setStatusById(id,"OCCUPIED");
    }




}
