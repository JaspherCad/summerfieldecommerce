package com.example.hoteltest.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltest.model.OrderEntity;
import com.example.hoteltest.model.User;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findByBuyer(User user);
	
	@Query("SELECT o FROM OrderEntity o JOIN o.orderItems oi WHERE oi.product.store.id = :sellerId")
	List<OrderEntity> findOrdersByStoreId(@Param("sellerId") Long sellerId);

	//BASE ON ENTITY NOT ON DATABASE NAME VARIABL
	//select o from OrderEntity o JOIN o.orderItems oi WHERE oi.prodct.seller.id = :sellerId.. ok tnx




}

	