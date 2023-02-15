package ir.maktab.finalprojectspring.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> InvalidInputExceptionHandler(InvalidInputException e) {
        CustomException exception = new CustomException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(exception, exception.httpStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundExceptionHandler(NotFoundException e) {
        CustomException exception = new CustomException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        return new ResponseEntity<>(exception, exception.httpStatus());
    }

    @ExceptionHandler(ObjectExistException.class)
    public ResponseEntity<?> ObjectExistExceptionHandler(ObjectExistException e) {
        CustomException exception = new CustomException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(exception, exception.httpStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> BindExceptionHandler(BindException e) {
        CustomException exception = new CustomException(HttpStatus.BAD_REQUEST, "validation failed");
        return new ResponseEntity<>(exception, exception.httpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> NullPointerExceptionHandler(NullPointerException e) {
        CustomException exception = new CustomException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(exception, exception.httpStatus());
    }

    // input not mapping to target definition
    @ExceptionHandler(MismatchedInputException.class)
    ResponseEntity<?> handleException(MismatchedInputException e) {
        CustomException exception = new CustomException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(exception, exception.httpStatus());
    }

    // wrong mapping HTTP request, for example method is GET but request is POST
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException e) {
        CustomException exception = new CustomException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(exception, exception.httpStatus());
    }

}
