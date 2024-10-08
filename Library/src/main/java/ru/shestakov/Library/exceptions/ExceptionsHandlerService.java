package ru.shestakov.Library.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@RestControllerAdvice
public class ExceptionsHandlerService extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<LibraryErrorResponse> handleAllReadyExistsException(BookAlReadyExistsInLibraryException e){
        LibraryErrorResponse response = new LibraryErrorResponse(
                "This is Book all ready exists in Library",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
}
