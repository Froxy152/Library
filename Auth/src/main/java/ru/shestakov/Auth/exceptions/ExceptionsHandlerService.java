package ru.shestakov.Auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class ExceptionsHandlerService extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleAllReadyExistsException(UserAllReadyExistsException e){
        UserErrorResponse response = new UserErrorResponse(
                "This User is exists",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
}
