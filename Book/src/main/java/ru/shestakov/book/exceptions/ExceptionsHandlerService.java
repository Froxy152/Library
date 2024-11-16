package ru.shestakov.book.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Component
@RestControllerAdvice
public class ExceptionsHandlerService extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundException e){
        BookErrorResponse response = new BookErrorResponse(
                "Book wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleAllReadyExistsException(BookAlReadyExistsException e){
        BookErrorResponse response = new BookErrorResponse(
                "This is Book all ready exists",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleUnavailableServiceException(ServiceUnvailabeException e){
        BookErrorResponse response = new BookErrorResponse(
                "Service unavailable",System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.SERVICE_UNAVAILABLE);

    }
}
