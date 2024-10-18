//package com.example.hoteltest.service;
//
//import com.example.hoteltest.dto.LoginRequest;
//import com.example.hoteltest.dto.Response;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.example.hoteltest.dto.UserDTO;
//import com.example.hoteltest.exceptiom.MyCustomException;
//import com.example.hoteltest.model.User;
//import com.example.hoteltest.repository.UserRepository;
//import com.example.hoteltest.service.jwtpackage.JWTUtils;
//import com.example.hoteltest.service.utils.Utils;
//
//@Service
//public class UserService {
//	 @Autowired
//	    private UserRepository userRepository;
//	    @Autowired
//	    private PasswordEncoder passwordEncoder;
//	    
//	    @Autowired
//	    private AuthenticationManager authenticationManager;
//
//
//	    //basically the idea is to get the info from UserDto (get.())
//	    //original user set(userDto.get)
//	    public Response register(UserDTO userDTO) {
//	        Response response = new Response();
//	        try {
//	            User user = new User();
//	            user.setFullName(userDTO.getFullName());
//	            user.setEmail(userDTO.getEmail());
//	            user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Password encryption
//	            user.setPhoneNumber(userDTO.getPhoneNumber());
//	            user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");
//	            user.setBlock(userDTO.getBlock());
//	            user.setLot(userDTO.getLot());
//
//
//	            if (userRepository.existsByEmail(user.getEmail())) {
//	                throw new MyCustomException(user.getEmail() + "  already exists");
//	            }
//
//	            User savedUser = userRepository.save(user);
//
//	            
//	            //for output
//	            UserDTO savedUserDTO = new UserDTO();
//	            savedUserDTO.setFullName(savedUser.getFullName());
//	            savedUserDTO.setEmail(savedUser.getEmail());
//	            savedUserDTO.setPhoneNumber(savedUser.getPhoneNumber());
//	            savedUserDTO.setRole(savedUser.getRole());
//	            savedUserDTO.setPassword(savedUser.getPassword());
//	            savedUserDTO.setBlock(savedUser.getBlock());
//	            savedUserDTO.setLot(savedUser.getLot());
//
//	            
//	            
//	            response.setStatusCode(200);
//	            response.setUser(savedUserDTO);  // Response with DTO to avoid sending all fields
//	        } catch (MyCustomException e) {
//	            response.setStatusCode(400);
//	            response.setMessage(e.getMessage());
//	        } catch (Exception e) {
//	            response.setStatusCode(500);
//	            response.setMessage("Error occurred during user registration: " + e.getMessage());
//	        }
//	        return response;
//	    }
//
//	    
//	    public Response login(LoginRequest loginRequest) {
//
//	        Response response = new Response();
//
//	        try {
//	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//	            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new MyCustomException("user Not found"));
//
//	            var token = jwtUtils.generateToken(user);
//	            response.setStatusCode(200);
//	            response.setToken(token);
//	            response.setRole(user.getRole());
//	            response.setExpirationTime("7 Days");
//	            response.setMessage("successful");
//
//	        } catch (MyCustomException e) {
//	            response.setStatusCode(404);
//	            response.setMessage(e.getMessage());
//
//	        } catch (Exception e) {
//
//	            response.setStatusCode(500);
//	            response.setMessage("Error Occurred During USer Login " + e.getMessage());
//	        }
//	        return response;
//	    }
//
//	    public Response getAllUsers() {
//
//	        Response response = new Response();
//	        try {
//	            List<User> userList = userRepository.findAll();
//	            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
//	            response.setStatusCode(200);
//	            response.setMessage("successful");
//	            response.setUserList(userDTOList);
//
//	        } catch (Exception e) {
//	            response.setStatusCode(500);
//	            response.setMessage("Error getting all users " + e.getMessage());
//	        }
//	        return response;
//	    }
//
//	    public Response getUserBookingHistory(String userId) {
//
//	        Response response = new Response();
//
//
//	        try {
//	            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new MyCustomException("User Not Found"));
//	            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
//	            response.setStatusCode(200);
//	            response.setMessage("successful");
//	            response.setUser(userDTO);
//
//	        } catch (MyCustomException e) {
//	            response.setStatusCode(404);
//	            response.setMessage(e.getMessage());
//
//	        } catch (Exception e) {
//
//	            response.setStatusCode(500);
//	            response.setMessage("Error getting all users " + e.getMessage());
//	        }
//	        return response;
//	    }
//
//	    public Response deleteUser(String userId) {
//
//	        Response response = new Response();
//
//	        try {
//	            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new MyCustomException("User Not Found"));
//	            userRepository.deleteById(Long.valueOf(userId));
//	            response.setStatusCode(200);
//	            response.setMessage("successful");
//
//	        } catch (MyCustomException e) {
//	            response.setStatusCode(404);
//	            response.setMessage(e.getMessage());
//
//	        } catch (Exception e) {
//
//	            response.setStatusCode(500);
//	            response.setMessage("Error getting all users " + e.getMessage());
//	        }
//	        return response;
//	    }
//
//	    public Response getUserById(Long userId) {
//
//	        Response response = new Response();
//
//	        try {
//	            User user = userRepository.findById(userId).orElseThrow(() -> new MyCustomException("User Not Found"));
//	            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
//	            response.setStatusCode(200);
//	            response.setMessage("successful");
//	            response.setUser(userDTO);
//
//	        } catch (MyCustomException e) {
//	            response.setStatusCode(404);
//	            response.setMessage(e.getMessage());
//
//	        } catch (Exception e) {
//
//	            response.setStatusCode(500);
//	            response.setMessage("Error getting all users " + e.getMessage());
//	        }
//	        return response;
//	    }
//
//	    public Response getMyInfo(String email) {
//
//	        Response response = new Response();
//
//	        try {
//	            User user = userRepository.findByEmail(email).orElseThrow(() -> new MyCustomException("User Not Found"));
//	            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
//	            response.setStatusCode(200);
//	            response.setMessage("successful");
//	            response.setUser(userDTO);
//
//	        } catch (MyCustomException e) {
//	            response.setStatusCode(404);
//	            response.setMessage(e.getMessage());
//
//	        } catch (Exception e) {
//
//	            response.setStatusCode(500);
//	            response.setMessage("Error getting all users " + e.getMessage());
//	        }
//	        return response;
//	    }
//	    
//	    
//	  //register seller;
//	    //(User currentUser) -> userRepository.findByEmail(currentUser.getEmail)
//	    //after getting the email, set user role into "SELLER"
//	    public Response registerSeller(User currentUser, String userEmail) {
//	        Response response = new Response();
//	        try {
//	        	User user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(() -> new RuntimeException("NAH doesn't exist!"));
//	           
//
//	            // Assign seller role
//	            user.setRole("SELLER");
//
//	            userRepository.save(user);
//
//	            response.setStatusCode(201);
//	            response.setMessage("Seller registered successfully");
//	            response.setUserPlain(user);
//	        } catch (Exception e) {
//	            response.setStatusCode(500);
//	            response.setMessage("Error during registration: " + e.getMessage());
//	        }
//	        return response;
//	    }
//	    
//	    public List<User> allUsers() {
//	        List<User> users = new ArrayList<>();
//
//	        userRepository.findAll().forEach(users::add);
//
//	        return users;
//	    }
//	}
//package com;

