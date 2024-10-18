package com.example.hoteltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hoteltest.model.Booking;

public interface BookingsRepository extends JpaRepository<Booking, Long>{

}
