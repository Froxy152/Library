package ru.shestakov.Authorization.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UserErrorResponse {
   private String message;
    private long time;
}
