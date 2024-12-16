package com.example.hoteltest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hoteltest.model.OrderItem;
import com.example.hoteltest.service.sellerdashboard.ProductSummary;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
//	existsByOrder_User_IdAndProduct_Id(userId, productId); FOR PRACTICE SAKE SA BABA HAHAAH
	@Query("SELECT CASE WHEN COUNT(oi) > 0 THEN true ELSE false END "  + //if tama then true, else false
			"FROM OrderItem oi "  +
			"WHERE oi.order.buyer.id = :userId AND oi.product.id = :productId")
	boolean didUserPurchasedProduct(@Param("userId") Long userId, @Param("productId") Long productId);
	
	
	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.ProductSummary(oi.product.id, oi.product.name, SUM(oi.quantity)) "
			+ "FROM OrderItem oi WHERE oi.order.store.id=:storeId "
			+ "GROUP BY oi.product.id ORDER BY SUM(oi.quantity) DESC"
			) //LONG not int
	List<ProductSummary> findTopProductsByStoreId(@Param("storeId")Long storeId); //get the highest quantity of orderitem with accepted status
//JPA requires providers to return long results for some when dealing with integral types.
	//JPA requires providers to return long results for some when dealing with integral types.
		//JPA requires providers to return long results for some when dealing with integral types.
}
