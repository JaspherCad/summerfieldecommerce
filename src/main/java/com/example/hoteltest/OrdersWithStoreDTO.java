package com.example.hoteltest;

import java.util.List;

import com.example.hoteltest.dto.OrderEntityDTO;
import com.example.hoteltest.dto.StoreResponseDTO;

public class OrdersWithStoreDTO {
    private List<OrderEntityDTO> orders;
    private StoreResponseDTO store;

    // Constructors
    public OrdersWithStoreDTO() {}

    public OrdersWithStoreDTO(List<OrderEntityDTO> orders, StoreResponseDTO store) {
        this.orders = orders;
        this.store = store;
    }

    // Getters and Setters
    public List<OrderEntityDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntityDTO> orders) {
        this.orders = orders;
    }

    public StoreResponseDTO getStore() {
        return store;
    }

    public void setStore(StoreResponseDTO store) {
        this.store = store;
    }
}
