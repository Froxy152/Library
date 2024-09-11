package ru.shestakov.Library.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class LibraryDTO {

    private String taken_at;

    private String return_at;
    private int book;

}
