package com.example.hoteltest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.model.User;
import com.example.hoteltest.service.OrderService;
import com.example.hoteltest.service.SellerDashboardService;
import com.example.hoteltest.service.sellerdashboard.RecentOrdersTable;
import com.example.hoteltest.service.sellerdashboard.SalesOverviewDTO;

@RestController
@RequestMapping("/api/sellers")
public class SellerDashboardController {
	@Autowired
    private SellerDashboardService dashboardService;
	
	@Autowired
	private OrderService orderService;

    @GetMapping("/{storeId}/dashboard")
    public ResponseEntity<SalesOverviewDTO> getDashboard(@PathVariable Long storeId) {
        SalesOverviewDTO dashboard = dashboardService.getSalesOverview(storeId);
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/tableorders-fordashboard")
    public List<RecentOrdersTable> findRecentProductOrdersByStoreUserIdForTableUsingDb(){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
    	return orderService.findRecentProductOrdersByStoreUserIdForTableUsingDb(currentUser.getId());
    }

	
}
