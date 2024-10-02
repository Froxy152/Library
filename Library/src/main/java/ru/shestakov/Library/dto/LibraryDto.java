package ru.shestakov.Library.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class LibraryDto {

    private long taken_at;
    private long return_at;
    private int book;

}
