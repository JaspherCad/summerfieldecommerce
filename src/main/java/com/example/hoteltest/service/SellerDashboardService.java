package com.example.hoteltest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hoteltest.repository.OrderItemRepository;
import com.example.hoteltest.repository.OrderRepository;
import com.example.hoteltest.service.sellerdashboard.OrderSummary;
import com.example.hoteltest.service.sellerdashboard.SalesOverviewDTO;

@Service
public class SellerDashboardService {
	
	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    
    public SalesOverviewDTO getSalesOverview(Long storeId) {
        SalesOverviewDTO overview = new SalesOverviewDTO();
        overview.setTotalSales(orderRepository.findByTotalSAles(storeId));
        overview.setTopProducts(orderItemRepository.findTopProductsByStoreId(storeId)); //returns dto

        //OrderEntity to DTO type
        List<OrderSummary> recentOrders = orderRepository.findByRecentOrdersByStore(storeId) //returns actual entity
                .stream()
                .map(order -> new OrderSummary(order.getId(), order.getCreatedAt(), order.getTotalPrice()))
                .collect(Collectors.toList());
        overview.setRecentOrders(recentOrders);

        return overview;
    }


}
