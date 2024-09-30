package ru.shestakov.book.dto;

import lombok.Data;

@Data
public class RequestBookDto {
    private String title;

    private String genre;

    private String author;

    private String description;

    private String isbn;
}
