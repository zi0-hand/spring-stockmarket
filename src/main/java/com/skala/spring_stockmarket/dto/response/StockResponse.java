package com.skala.spring_stockmarket.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

// mapper랑 순서만 맞추면 이름이 달라도 된다 
@Getter
@AllArgsConstructor
public class StockResponse {
    private UUID id; 
    private String stockName;
    private int stockPrice;
}
