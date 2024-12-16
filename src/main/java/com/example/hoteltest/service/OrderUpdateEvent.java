package com.example.hoteltest.service;

import org.springframework.context.ApplicationEvent;

import com.example.hoteltest.dto.OrderEntityDTO;

public class OrderUpdateEvent extends ApplicationEvent {
    private final OrderEntityDTO orderDto;

    public OrderUpdateEvent(Object source, OrderEntityDTO orderDto) {
        super(source);
        this.orderDto = orderDto;
    }

    public OrderEntityDTO getOrderDto() {
        return orderDto;
    }
}