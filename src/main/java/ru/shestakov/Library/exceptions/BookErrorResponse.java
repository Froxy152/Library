package ru.shestakov.Library.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
public class BookErrorResponse {
    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private long errorTimeStamp;
}
