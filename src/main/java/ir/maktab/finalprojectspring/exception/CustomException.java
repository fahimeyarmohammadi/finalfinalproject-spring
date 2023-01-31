package ir.maktab.finalprojectspring.exception;

import org.springframework.http.HttpStatus;

public record CustomException(HttpStatus httpStatus, String message){
}
