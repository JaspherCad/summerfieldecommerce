package com.example.hoteltest.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltest.model.OrderEntity;
import com.example.hoteltest.model.User;
import com.example.hoteltest.service.sellerdashboard.OrderSummary;
import com.example.hoteltest.service.sellerdashboard.ProductSummary;
import com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO;
import com.example.hoteltest.service.sellerdashboard.RecentOrdersTable;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findByBuyer(User user);
	List<OrderEntity> findByStoreUserId(Long userId);

	
	@Query("SELECT o FROM OrderEntity o JOIN o.orderItems oi WHERE oi.product.store.id = :sellerId")
	List<OrderEntity> findOrdersByStoreId(@Param("sellerId") Long sellerId);

	//BASE ON ENTITY NOT ON DATABASE NAME VARIABL
	//select o from OrderEntity o JOIN o.orderItems oi WHERE oi.prodct.seller.id = :sellerId.. ok tnx

	
	
	
	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.RecentOrdersTable(" +
		       "p.name, o.id, o.createdAt, o.status, o.totalPrice) " +
		       "FROM OrderEntity o " +
		       "JOIN o.orderItems oi " +
		       "JOIN oi.product p " +
		       "WHERE o.store.user.id = :userId " +
		       "ORDER BY o.createdAt DESC")
		List<RecentOrdersTable> findRecentProductOrdersByStoreUserIdForTable(@Param("userId") Long userId);


	
	
	
	//SELECT this is like RETURN this
	
	@Query("SELECT SUM(o.totalPrice) FROM OrderEntity o JOIN o.orderItems oi WHERE oi.product.store.id = :storeId AND o.status = 'ACCEPTED'")
	BigDecimal findByTotalSAles(@Param("storeId") Long sellerId);
	
	@Query("SELECT o FROM OrderEntity o WHERE o.store.id = :storeId and o.status = 'ACCEPTED' ORDER BY o.createdAt DESC")
	List<OrderEntity> findByRecentOrdersByStore(@Param("storeId") Long storeId);
	//SELECT this is like RETURN this
	//    @Query("SELECT p FROM Product p JOIN OrderItem oi ON p.id = oi.product.id GROUP BY p.id ORDER BY SUM(oi.quantity) DESC")

			//	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.ProductSummary(oi.product.id, oi.product.name, SUM(oi.quantity)) " +
			//			"FROM OrderItem oi WHERE oi.order.store.seller"
			//			
			//			)
			//
			//    List<ProductSummary> findTopProductsBySeller(@Param("sellerId") Long sellerId);

	
	
	
	
	
	
	
	
	
	
	
	
//	RANKING CUSTOMERS
//	RANKING CUSTOMERS
//	RANKING CUSTOMERS

	//most frequent customer on shop //LONG TYPE DIGIT
	@Query("SELECT o.buyer AS buyer, COUNT(o) AS orderCount " +
		       "FROM OrderEntity o " +
		       "WHERE o.store.id = :storeId " +
		       "GROUP BY o.buyer " +
		       "ORDER BY orderCount DESC")
	List<User> findCustomersWithOrderCountsByStoreId(@Param("storeId") Long storeId);
	
	//most high spending customer //DOUBLE TYPE DIGIT kasi SUM
	@Query("SELECT o.buyer AS buyer, SUM(o.totalPrice) AS totalRevenue "
			+ "FROM OrderEntity o "
			+ "WHERE o.store.id = :storeId "
			+ "GROUP BY o.buyer "
			+ "ORDER BY totalRevenue DESC")
	List<User> findCustomersWithHighestSpentOnShop(@Param("storeId") Long storeId);
	
	

	
	
//	RANKING CUSTOMERS
//	RANKING CUSTOMERS
//	RANKING CUSTOMERS

	
	
	
	


	
//To identify loyal or frequent customers.
	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO(" +
		       "o.buyer.id, o.buyer.fullName, o.buyer.email, COUNT(o)) " +
		       "FROM OrderEntity o " +
		       "WHERE o.store.id = :storeId " +
		       "GROUP BY o.buyer " +
		       "ORDER BY COUNT(o) DESC")
	Page<RankingCustomerDTO> rankCustomersByOrderCount(@Param("storeId") Long storeId, Pageable pageable);

//for profit ranking	
	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO(" +
		       "o.buyer.id, o.buyer.fullName, o.buyer.email, SUM(o.totalPrice)) " +
		       "FROM OrderEntity o " +
		       "WHERE o.store.id = :storeId " +
		       "GROUP BY o.buyer " +
		       "ORDER BY SUM(o.totalPrice) DESC")
	Page<RankingCustomerDTO> rankCustomersByRevenueGive(@Param("storeId") Long storeId, Pageable pageable);
	
//for profit ranking
	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO(" +
		       "o.buyer.id, o.buyer.fullName, o.buyer.email, SUM(o.totalPrice - o.cost)) " +
		       "FROM OrderEntity o " +
		       "WHERE o.store.id = :storeId " +
		       "GROUP BY o.buyer " +
		       "ORDER BY SUM(o.totalPrice - o.cost) DESC")
	Page<RankingCustomerDTO> rankCustomersByProfit(@Param("storeId") Long storeId, Pageable pageable);
	
	
//JUST FOR TOTAL RANKING
	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO(" +
		       "o.buyer.id, o.buyer.fullName, o.buyer.email, COUNT(o) * 0.3 + SUM(o.totalPrice) * 0.5 + AVG(o.totalPrice) * 0.2) " +
		       "FROM OrderEntity o " +
		       "WHERE o.store.id = :storeId " +
		       "GROUP BY o.buyer " +
		       "ORDER BY COUNT(o) * 0.4 + SUM(o.totalPrice) * 0.6 DESC")
	Page<RankingCustomerDTO> rankCustomersByWeightedScore(@Param("storeId") Long storeId, Pageable pageable);
	
	
//TO FIND RECENT OR ACTIVE USERS.
	@Query("SELECT new com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO(" +
		       "o.buyer.id, o.buyer.fullName, o.buyer.email, MAX(o.createdAt)) " +
		       "FROM OrderEntity o " +
		       "WHERE o.store.id = :storeId " +
		       "GROUP BY o.buyer " +
		       "ORDER BY MAX(o.createdAt) DESC")
	Page<RankingCustomerDTO> rankCustomersByRecencyActive(@Param("storeId") Long storeId, Pageable pageable);
	
	

}

	