package com.example.hoteltest.authapi;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hoteltest.dto.LoginUserDTO;
import com.example.hoteltest.dto.RegisterUserDto;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.UserDTO;
import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Response signup(RegisterUserDto userDTO) {
    	Response response = new Response();
        try {
            User user = new User();
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Password encryption
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");
            user.setBlock(userDTO.getBlock());
            user.setLot(userDTO.getLot());


            if (userRepository.existsByEmail(user.getEmail())) {
                throw new MyCustomException(user.getEmail() + "  already exists");
            }

            User savedUser = userRepository.save(user);

            
            //for output
            UserDTO savedUserDTO = new UserDTO();
            savedUserDTO.setFullName(savedUser.getFullName());
            savedUserDTO.setEmail(savedUser.getEmail());
            savedUserDTO.setPhoneNumber(savedUser.getPhoneNumber());
            savedUserDTO.setRole(savedUser.getRole());
            savedUserDTO.setPassword(savedUser.getPassword());
            savedUserDTO.setBlock(savedUser.getBlock());
            savedUserDTO.setLot(savedUser.getLot());

            
            
            response.setStatusCode(200);
            response.setUser(savedUserDTO);  // Response with DTO to avoid sending all fields
        } catch (MyCustomException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration: " + e.getMessage());
        }
        return response;
    }

    public User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
