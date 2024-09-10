package ru.shestakov.Library.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class BookDTO {

    private String title;

    private String genre;

    private String author;

    private String description;

    private String isbn;

    private String status;

}
