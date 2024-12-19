package com.example.hoteltest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hoteltest.model.User;

public interface UserRepository extends JpaRepository<User, Long>{ 
	boolean existsByEmail(String email);
	Optional<User>  findByEmail(String email);
	List<User> findByRole(String role);
	
	
	@EntityGraph(attributePaths = {"orders", "bookings", "reviews"})
    Optional<User> findById(Long id);
}
