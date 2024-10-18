package com.example.hoteltest.authapi;
import org.springframework.http.ResponseEntity;
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

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        		loginResponse.setToken(jwtToken);
        		loginResponse.setExpiresIn(jwtService.getExpirationTime());
        		loginResponse.setUserId(authenticatedUser.getId());

        return ResponseEntity.ok(loginResponse);
    }
    
    @GetMapping("/test")
    public String authenticate() {
     

        return "OK??";
    }
}
