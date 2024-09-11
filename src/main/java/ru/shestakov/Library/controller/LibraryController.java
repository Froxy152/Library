package ru.shestakov.Library.controller;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.dto.LibraryDTO;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {
    private final LibraryService libraryService;
    private final ModelMapper modelMapper;
    @Autowired
    public LibraryController(LibraryService libraryService, ModelMapper modelMapper) {
        this.libraryService = libraryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<LibraryDTO> getFreeListBooks(){
        return libraryService.showAllFreeBooks().stream().map((this::convertToLibraryDto)).collect(Collectors.toList());
    }

    @Transactional
    @PatchMapping("/set/{id}")
    public void setOc(@PathVariable Integer id){
        libraryService.setStatusById(id, "oc");
    }

    public LibraryDTO convertToLibraryDto(Library library){
        return modelMapper.map(library,LibraryDTO.class);
    } public Library convertToLibrary(LibraryDTO libraryDTO){
        return modelMapper.map(libraryDTO,Library.class);
    }


}
