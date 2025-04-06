package com.skala.spring_stockmarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerStockResponse {

    private String playerNickname;
    private int playerMoney;
    private String stockName;
    private int stockPrice;
    private int stockQuantity;
    private int totalStockQuantity;    
    
}
