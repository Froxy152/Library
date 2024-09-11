package ru.shestakov.Library.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDto {

    private String title;

    private String genre;

    private String author;

    private String description;

    private String isbn;

    private String status;

}
