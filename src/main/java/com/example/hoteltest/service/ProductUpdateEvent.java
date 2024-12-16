package com.example.hoteltest.service;

import org.springframework.context.ApplicationEvent;

import com.example.hoteltest.dto.ProductDTO;


//ProductUpdateEvent
//Purpose: This is a custom event class that extends ApplicationEvent. It carries information about the product update.
//Structure: It contains a ProductDTO, which encapsulates the details of the product that has been added or updated.


public class ProductUpdateEvent extends ApplicationEvent {
    private final ProductDTO productDto;
//    Summary of the Flow
//    Step 1: A product is added via the addProduct method.
//    Step 2: ProductUpdateEvent is created and published using ApplicationEventPublisher.
//    Step 3: The event listener (handleProductUpdateEvent) reacts to the published event and notifies all subscribers (clients connected to the SSE endpoint).
    public ProductUpdateEvent(Object source, ProductDTO productDto) {
        super(source);							//FILLED NA. MAY LAMAN
        this.productDto = productDto;
    }

    public ProductDTO getProductDto() {
        return productDto;
    }
}
