package com.example.hoteltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hoteltest.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Store findByUserId(Long userId);

}
