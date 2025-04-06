package com.skala.spring_stockmarket.dto.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class BuyPlayerStockRequest {

    private UUID playerId; 
    private UUID stockId;
    private int stockQuantity;
    
}
