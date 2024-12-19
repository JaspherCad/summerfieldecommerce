package com.example.hoteltest.authapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.dto.BookingsDto;
import com.example.hoteltest.dto.OrderEntityDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.ReviewDTO;
import com.example.hoteltest.dto.UserDTO;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("UserController initialized");

    }

    
    
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    
    }
    
    
//    @GetMapping("/me")
//    public ResponseEntity<User> authenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("LOOK HERE" + authentication);
//        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        
//        User currentUser = (User) authentication.getPrincipal();
//        User userWithDetails = userService.getUserWithDetails(currentUser.getId());
//        UserDTO userDto = new UserDTO(userWithDetails);// Fetch with lazy-loaded fields initialized
//
////        userDto.setListOfBookings(userWithDetails.getBookings()
////                .stream()
////                .map(booking -> new BookingsDto(booking)) // Assuming BookingsDto has a constructor
////                .collect(Collectors.toList()));
////        
//            userDto.setReviews(userWithDetails.getReviews()
//            	.stream()
//                .map(review -> new ReviewDTO(review)) // Assuming ReviewDTO has a constructor
//                .collect(Collectors.toList()));
//            
//            
//            userDto.setOrders(userWithDetails.getOrders()
//                .stream()
//                .map(order -> new OrderEntityDTO(order)) // Assuming OrderEntityDTO has a constructor
//                .collect(Collectors.toList()));
//        return ResponseEntity.ok(currentUser); // Return the initialized object
//    }

//    @GetMapping("/")
//    public ResponseEntity<Response> allUsers() {
//        Response users = userService.getAllUsers();
//
//        return ResponseEntity.ok(users);
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


  @Autowired 
  private UserRepository userRepository;

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Response> getAllUsers() {
      Response response = userService.getAllUsers();
      return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-by-id/{userId}")
  public ResponseEntity<Response> getUserById(@PathVariable("userId") Long userId) {
      Response response = userService.getUserById(userId);
      return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @DeleteMapping("/delete/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Response> deleteUSer(@PathVariable("userId") String userId) {
      Response response = userService.deleteUser(userId);
      return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-logged-in-profile-info")
  public ResponseEntity<Response> getLoggedInUserProfile() {

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String email = authentication.getName();
      Response response = userService.getMyInfo(email);
      return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-user-bookings/{userId}")
  public ResponseEntity<Response> getUserBookingHistory(@PathVariable("userId") String userId) {
      Response response = userService.getUserBookingHistory(userId);
      return ResponseEntity.status(response.getStatusCode()).body(response);
  }
  
  @PostMapping("/register-seller/{userEmail}")
  public ResponseEntity<Response> registerToSeller(@PathVariable String userEmail) {
  	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      User currentUser = (User) authentication.getPrincipal();
      
      
      //manual validation by the admin is a MUST! implement soon
      Response response = userService.registerSeller(currentUser, userEmail);
      return ResponseEntity.status(response.getStatusCode()).body(response);
  	
  }
    
    
    
    
    
    
    
    
    
}
