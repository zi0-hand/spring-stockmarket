package com.skala.spring_stockmarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerStockListResponse {
    
    private String stockName;
    private int stockPrice;
    private int stockQuantity;
    private int profitRate;
    private int totalInvestment;
    
}
