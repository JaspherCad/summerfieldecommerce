package com.example.hoteltest.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hoteltest.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	
						//	SELECT * 
						//	FROM room 
						//	WHERE id NOT IN (SELECT room_id FROM bookings);
	 @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
	    List<Room> findRoomsNotBooked();
	 
	 					// SELECT DISTINCT * from ROOM ;

	 @Query("SELECT DISTINCT r.roomType FROM Room r")
	    List<Room> findDistinctRooms();
	 
	 @Query("SELECT r FROM Room r WHERE r.roomType = :roomType " +
	           "AND r.id NOT IN (SELECT b.room.id FROM Booking b " + //NOT IN (SELEC.. means this queries returns all the unavailable rooms?
	           "WHERE (b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate))")
	    List<Room> findAvailableRoomsByDatesAndRoomType(@Param("checkInDate") LocalDate checkInDate,
	                                                    @Param("checkOutDate") LocalDate checkOutDate,
	                                                    @Param("roomType") String roomType);
}
