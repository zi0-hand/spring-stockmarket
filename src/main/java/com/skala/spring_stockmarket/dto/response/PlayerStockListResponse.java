package com.skala.spring_stockmarket.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerStockListResponse {
    private UUID stockId; 
    private String stockName;
    private int stockPrice;
    private int stockQuantity;
    private int profitRate;
    private int totalInvestment;
    
}
