package com.example.hoteltest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.User;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByStore(Store store);
    @Query("SELECT p FROM Product p JOIN p.tags t WHERE t.name = :tagName")
    List<Product> findByTagName(@Param("tagName") String tagName);

    //can be      List<Product> findByTag_Name(String tagName);

    @Query("SELECT p FROM Product p WHERE p.isFeatured = true")
    List<Product> findFeaturedProducts();
    
    
    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findNewArrivals(Pageable pageable);
    
    @Query("SELECT p FROM Product p JOIN OrderItem oi ON p.id = oi.product.id GROUP BY p.id ORDER BY SUM(oi.quantity) DESC")
    List<Product> findBestSellingProducts(Pageable pageable);

}
