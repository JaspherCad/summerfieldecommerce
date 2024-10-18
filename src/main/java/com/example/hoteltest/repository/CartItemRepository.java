package com.example.hoteltest.repository;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hoteltest.model.Cart;
import com.example.hoteltest.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	Optional<List<CartItem>> findByCart(Cart cart);
	

}
