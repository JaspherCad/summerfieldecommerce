package com.example.hoteltest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.authapi.UserService;
import com.example.hoteltest.dto.LoginRequest;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.UserDTO;
import com.example.hoteltest.model.User;

@RestController
@RequestMapping("/authv2")
public class AuthController {

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<Response> register(@RequestBody UserDTO userDTO) {
//        Response response = userService.register(userDTO);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
//        Response response = userService.login(loginRequest);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
}
