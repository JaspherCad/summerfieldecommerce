package com.example.hoteltest.authapi;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.UserDTO;
import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.UserRepository;
import com.example.hoteltest.service.utils.Utils;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    
    
    
    
    
    
    
    
    
	
    
    @Autowired
    private AuthenticationManager authenticationManager;


    

    public Response getAllUsers() {

        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    public Response getUserBookingHistory(String userId) {

        Response response = new Response();


        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new MyCustomException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (MyCustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    public Response deleteUser(String userId) {

        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new MyCustomException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (MyCustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    public Response getUserById(Long userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new MyCustomException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (MyCustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    public Response getMyInfo(String email) {

        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new MyCustomException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (MyCustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }
    
    
  //register seller;
    //(User currentUser) -> userRepository.findByEmail(currentUser.getEmail)
    //after getting the email, set user role into "SELLER"
    public Response registerSeller(User currentUser, String userEmail) {
        Response response = new Response();
        try {
        	User user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(() -> new RuntimeException("NAH doesn't exist!"));
           

            // Assign seller role
            user.setRole("SELLER");

            userRepository.save(user);

            response.setStatusCode(201);
            response.setMessage("Seller registered successfully");
            response.setUserPlain(user);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error during registration: " + e.getMessage());
        }
        return response;
    }
    
    public List<User> allUsers2() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}