package com.skala.spring_stockmarket.dto.request;

import lombok.Getter;

@Getter
public class CreateStockRequest {
    
    private String name;
    private int price;
    private String description;

}
