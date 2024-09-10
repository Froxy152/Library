package ru.shestakov.Library.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

public class LibraryDTO {

    private String taken_at;

    private String return_at;
    private int book;

}
