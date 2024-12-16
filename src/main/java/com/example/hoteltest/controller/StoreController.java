package com.example.hoteltest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.StoreRequestDTO;
import com.example.hoteltest.dto.StoreResponseDTO;
import com.example.hoteltest.model.User;
import com.example.hoteltest.service.StoreService;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SELLER')")
    public Response createStore(
            @RequestBody StoreRequestDTO storeRequestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();

        // Call the service to create a store
        Response storeResponse = storeService.createStore(currentUser, storeRequestDTO);
        return storeResponse;

    }
    @GetMapping("/myStore")
    @PreAuthorize("hasAuthority('SELLER')")
    public Response myStore() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    
	    Response storeResponseDTO = storeService.myStore(currentUser);
	    return storeResponseDTO;
    	
    }
    
    
}