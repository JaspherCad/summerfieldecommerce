package com.example.hoteltest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hoteltest.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
	
    Optional<Tag> findByName(String name);


}
