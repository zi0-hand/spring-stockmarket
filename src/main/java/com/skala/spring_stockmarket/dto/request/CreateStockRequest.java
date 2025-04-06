package com.skala.spring_stockmarket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateStockRequest {
    
    private String name;
    private int price;
    private String description;

}
