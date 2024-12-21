package com.example.hoteltest.exceptiom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


//Error Response Class
class ErrorResponse {
 private int statusCode;
 private String message;

 public ErrorResponse(int statusCode, String message) {
     this.statusCode = statusCode;
     this.message = message;
 }

 public int getStatusCode() {
     return statusCode;
 }

 public String getMessage() {
     return message;
 }
}

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException {
        // Custom error response
    	
    	Boolean isJwtMissing = (Boolean) request.getAttribute("isJwtMissing");
    	Boolean isRefreshMissing = (Boolean) request.getAttribute("isRefreshMissing");

        int statusCode = 403; // Default to 403
        
        if ((isJwtMissing != null && isJwtMissing) && (isRefreshMissing != null && isRefreshMissing == false)) {
            statusCode = 498; // Custom 407 status code for missing JWT
        }
    	
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write("{\"statusCode\": 401, \"error\": \"Unauthorized\", \"message\": \"Invalid or missing JWT token.\"}");
        ErrorResponse errorResponse = new ErrorResponse(statusCode, "Authentication Failed");
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

    }
}
