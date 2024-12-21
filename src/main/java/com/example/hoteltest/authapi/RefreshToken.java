package com.example.hoteltest.authapi;

import java.time.Instant;
import com.example.hoteltest.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private Instant expiryDate;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true) // Ensures uniqueness
    private User userInfo;

    // Default constructor
    public RefreshToken() {}

    // All-args constructor
    public RefreshToken(int id, String token, Instant expiryDate, User userInfo) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.userInfo = userInfo;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    // Builder static inner class
    public static class Builder {
        private int id;
        private String token;
        private Instant expiryDate;
        private User userInfo;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder userInfo(User userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(id, token, expiryDate, userInfo);
        }
    }

    // Static builder() method
    public static Builder builder() {
        return new Builder();
    }
}
