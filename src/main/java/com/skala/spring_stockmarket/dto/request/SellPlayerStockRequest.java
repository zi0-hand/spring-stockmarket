package com.skala.spring_stockmarket.dto.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class SellPlayerStockRequest {
    private UUID playerId;
    private UUID stockId;
    private int stockQuantity;
}
