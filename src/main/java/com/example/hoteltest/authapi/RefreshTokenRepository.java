package com.example.hoteltest.authapi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserInfoId(Long id);


}