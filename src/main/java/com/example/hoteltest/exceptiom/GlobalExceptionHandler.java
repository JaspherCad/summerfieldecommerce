package com.example.hoteltest.exceptiom;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;

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
    
 // Handle ExpiredTokenException with status 407
    @ExceptionHandler(ExpiredTokenException.class)
    public void handleExpiredTokenException(HttpServletResponse response, ExpiredTokenException ex) throws IOException {
        response.setStatus(407);
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(407, "The JWT token has expired", ex.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
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
    
    
    
    
 // ErrorResponse class
    public static class ErrorResponse {
        private int statusCode;
        private String description;
        private String message;

        public ErrorResponse(int statusCode, String description, String message) {
            this.statusCode = statusCode;
            this.description = description;
            this.message = message;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
 

   


