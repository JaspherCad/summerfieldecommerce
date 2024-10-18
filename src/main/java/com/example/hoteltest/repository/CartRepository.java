package com.example.hoteltest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hoteltest.model.Cart;
import com.example.hoteltest.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUser(User user);


}
