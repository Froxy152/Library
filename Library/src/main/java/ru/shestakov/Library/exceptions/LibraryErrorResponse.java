package ru.shestakov.Library.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LibraryErrorResponse {
    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private long errorTimeStamp;
}
