package com.example.hoteltest.authapi;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.UserRepository;











//In summary, the RefreshTokenService class 
//provides methods to create, find, and verify the expiration of refresh tokens.






@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public RefreshToken createRefreshToken(String username){
    	
    	//check existing token
    	User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    	
    	RefreshToken existingToken = refreshTokenRepository.findByUserInfoId(user.getId()).orElse(null);


        
    	//if token exists, update existing token
        if (existingToken != null) {

        	// Update the token and expiry date of the existing refresh token
            existingToken.setToken(UUID.randomUUID().toString());
            existingToken.setExpiryDate(Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000)); // 10 minutes
            return refreshTokenRepository.save(existingToken);

        }

     // If no token exists, create a new one
        RefreshToken newRefreshToken  = RefreshToken.builder()
                .userInfo(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file 
                .build();
        return refreshTokenRepository.save(newRefreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}