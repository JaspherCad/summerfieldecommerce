//package com.example.hoteltest.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.hoteltest.dto.Response;
//import com.example.hoteltest.dto.UserDTO;
//import com.example.hoteltest.model.User;
//import com.example.hoteltest.repository.UserRepository;
//import com.example.hoteltest.service.UserService;
//
//@RestController
//@RequestMapping("/usersv2")
//public class UserController {
//
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired 
//    private UserRepository userRepository;
//
//    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<Response> getAllUsers() {
//        Response response = userService.getAllUsers();
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @GetMapping("/get-by-id/{userId}")
//    public ResponseEntity<Response> getUserById(@PathVariable("userId") Long userId) {
//        Response response = userService.getUserById(userId);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @DeleteMapping("/delete/{userId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<Response> deleteUSer(@PathVariable("userId") String userId) {
//        Response response = userService.deleteUser(userId);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @GetMapping("/get-logged-in-profile-info")
//    public ResponseEntity<Response> getLoggedInUserProfile() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        Response response = userService.getMyInfo(email);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @GetMapping("/get-user-bookings/{userId}")
//    public ResponseEntity<Response> getUserBookingHistory(@PathVariable("userId") String userId) {
//        Response response = userService.getUserBookingHistory(userId);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//    
//    @PostMapping("/register-seller/{userEmail}")
//    public ResponseEntity<Response> registerToSeller(@PathVariable String userEmail) {
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//        
//        
//        //manual validation by the admin is a MUST! implement soon
//        Response response = userService.registerSeller(currentUser, userEmail);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    	
//    }
//
//
//}