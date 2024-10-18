package com.example.hoteltest.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.repository.UserRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(username).orElseThrow(() -> new MyCustomException("not found email: " + username));
	}
	 
}
