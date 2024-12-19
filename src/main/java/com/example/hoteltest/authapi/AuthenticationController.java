package com.example.hoteltest.authapi;
import java.util.Arrays;

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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, CustomUserDetailsService customUserDetailsService, RefreshTokenService refreshTokenService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.customUserDetailsService = customUserDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;

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
	        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginUserDto.getEmail());
	        // Create HTTP-only cookies for the tokens
	        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
	                .httpOnly(true)
	                .secure(false) // Set to true in production
	                .path("/")
	                .maxAge(30*60) // 30 mins
	                .sameSite("Strict")
	                .build();
	
	        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
	                .httpOnly(true)
	                .secure(false) // Set to true in production
	                .path("/")
	                .maxAge(7 * 24 * 60 * 60) // 7 days
	                .sameSite("Strict")
	                .build();
	
	        // 
	        Map<String, Object> responseBody = new HashMap<>();
	        responseBody.put("userId", authenticatedUser.getId());
	        responseBody.put("message", "Login successful!");
	        responseBody.put("expiresIn", jwtService.getExpirationTime());
	
	        // Return response with cookies set in the headers
	        return ResponseEntity.ok()
	                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
	//                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
	                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
	                .body(responseBody);
	    }
    
    
    
    

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        // Extract the refresh token from cookies
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                	UserDetails userDetails = userService.loadUserByUsername(userInfo.getEmail()); // Fetch user details
                	String accessToken = jwtService.generateToken(userDetails); // Generate new JWT
                	
                	
                    // Create new HTTP-only cookie for the JWT
                    ResponseCookie newJwtCookie = ResponseCookie.from("jwt", accessToken)
                            .httpOnly(true)
                            .secure(false) // Set to true in production
                            .path("/")
                            .maxAge(10) // Token expiry in seconds (10 minutes)
                            .sameSite("Strict")
                            .build();

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("userId", userInfo.getId());
                    responseBody.put("message", "Token refreshed successfully!");
                    responseBody.put("expiresIn", jwtService.getExpirationTime());

                    // Return response with cookies set in the headers
                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, newJwtCookie.toString())
                            .body(responseBody);
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }


//  @PostMapping("/refreshToken")
//  public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
//      return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
//              .map(refreshTokenService::verifyExpiration)
//              .map(RefreshToken::getUserInfo)
//              .map(userInfo -> {
//                  String accessToken = jwtService.GenerateToken(userInfo.getUsername());
//                  return JwtResponseDTO.builder()
//                          .accessToken(accessToken)
//                          .token(refreshTokenRequestDTO.getToken()).build();
//              }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
//  }
  
  //OUR DIFFERENCE: Here they send the TOKEN from POST request because this is not cookies. 
  //this is default JWT way (my old process)
  //BUT now since i am using cookies, lets get the JWT token from our cookies
  
    
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
