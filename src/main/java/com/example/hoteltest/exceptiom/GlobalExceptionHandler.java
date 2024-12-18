package com.example.hoteltest.exceptiom;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice 
public class GlobalExceptionHandler {

  
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401 
                .body(Map.of(
                    "error", "Token has expired. Please log in again.",
                    "timestamp", System.currentTimeMillis()
                ));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex) {
    	System.err.println("NullPointerException occurred: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 
                .body(Map.of(
                    "error", "Something went wrong. Please try again later.",
                    "details", ex.getMessage(),
                    "timestamp", System.currentTimeMillis()
                ));
    }
    
    
//    @ExceptionHandler(MyCustomException.class)
//    public ResponseEntity<?> handleMyCustomException(MyCustomException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 status code
//                .body(Map.of(
//                    "error", "A custom error occurred",
//                    "message", ex.getMessage(),
//                    "timestamp", System.currentTimeMillis()
//                ));
//    }

}
