package com.example.hoteltest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.authapi.UserService;
import com.example.hoteltest.model.User;
import com.example.hoteltest.model.UserLog;

@RestController
@RequestMapping("/api/user/logs")
public class UserLogController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserLog>> getUserLogs(@PathVariable Long userId) {
        List<UserLog> logs = userService.getUsersLogByUserId(userId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping()
    public ResponseEntity<List<UserLog>> getUserLogsOfUser(@PathVariable Long userId) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<UserLog> logs = userService.getUsersLogByUserId(currentUser.getId());
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/actions/{userId}/{action}")
    public ResponseEntity<List<UserLog>> getUserLogsAction(@PathVariable Long userId, @PathVariable String action ) {
    	List<UserLog> logs = userService.getUserLogsByAction(userId, action);
        return ResponseEntity.ok(logs);
    }
}
