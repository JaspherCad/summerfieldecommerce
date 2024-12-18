package com.example.hoteltest.authapi;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.dto.LoginUserDTO;
import com.example.hoteltest.dto.RegisterUserDto;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.UserDTO;
import com.example.hoteltest.model.User;
import com.example.hoteltest.service.CustomUserDetailsService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.customUserDetailsService = customUserDetailsService;
    }
    
    
    

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterUserDto registerUserDto) {
    	try {
            System.out.println("BACKEND: Reached signup endpoint");
            System.out.println("BACKEND: Request received for user: " + registerUserDto);

            Response registeredUser = authenticationService.signup(registerUserDto);
            System.out.println("BACKEND: Request received for user: " + registerUserDto);

            return ResponseEntity.status(registeredUser.getStatusCode()).body(registeredUser);
        } catch (Exception e) {
        	Response response = new Response();
        	response.setMessage("error from backed registed");
            System.out.println("BACKEND: Error occurred during signup: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
        
    }
    
    
    
    
    
    
    
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
//        User authenticatedUser = authenticationService.authenticate(loginUserDto);
//
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//        String jwtRefresher = jwtService.generateRefreshToken(authenticatedUser);
//        
//        LoginResponse loginResponse = new LoginResponse();
//        		loginResponse.setToken(jwtToken);
//        		loginResponse.setExpiresIn(jwtService.getExpirationTime());
//        		loginResponse.setUserId(authenticatedUser.getId());
//        		loginResponse.setRefreshToken(jwtRefresher);
//        return ResponseEntity.ok(loginResponse);
//    } OLD CODE
    

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Generate JWT and Refresh Tokens
        String jwtToken = jwtService.generateToken(authenticatedUser);
        String jwtRefresher = jwtService.generateRefreshToken(authenticatedUser);

        // Create HTTP-only cookies for the tokens
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .secure(false) // Set to true in production
                .path("/")
                .maxAge(600000) // Token expiry in seconds
                .sameSite("Strict")
                .build();

//        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwtRefresher)
//                .httpOnly(true)
//                .secure(false) // Set to true in production
//                .path("/")
//                .maxAge(7 * 24 * 60 * 60) // 7 days for the refresh token
//                .sameSite("Strict")
//                .build();

        // 
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userId", authenticatedUser.getId());
        responseBody.put("message", "Login successful!");
        responseBody.put("expiresIn", jwtService.getExpirationTime());

        // Return response with cookies set in the headers
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(responseBody);
    }

    
    @GetMapping("/test")
    public String authenticate() {
     

        return "OK??";
    }
    
    
    
    
    
    
    
    
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRequest tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();
        String userEmail = jwtService.extractUsername(refreshToken);
        //returns EMAIL
        // Load the user details from your user service
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail); //LOAD BY MAIL

        // Validate the refresh token
        if (jwtService.isTokenValid(refreshToken, userDetails)) {
            // Generate a new access token
            String token = jwtService.generateRefreshToken(userDetails);
            return ResponseEntity.ok(new TokenResponse(token, refreshToken));
        } else {
            // Handle invalid refresh token case
            Response response = new Response();
            response.setMessage("Error from backend: Registered refresh token is invalid");
            System.out.println("BACKEND: Error occurred during token refresh");
            return ResponseEntity.ok(response);
        }
    }

    
    private static class TokenRequest {
        private String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    
    private static class TokenResponse {
        private String token;
        private String refreshToken;

        public TokenResponse(String accessToken, String refreshToken) {
            this.token = accessToken;
            this.refreshToken = refreshToken;
        }

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getRefreshToken() {
			return refreshToken;
		}

		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

        
    }
    
    
    
    
}
