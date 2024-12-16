package com.example.hoteltest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hoteltest.model.UserLog;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
	List<UserLog> findByUserId(Long userId);
	List<UserLog> findByUserIdAndAction(Long userId, String action);
}
