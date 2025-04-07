// PlayerStockResponse.java
package com.skala.spring_stockmarket.dto.response;

public record PlayerStockResponse(
    String playerNickname,
    int playerMoney,
    String stockName,
    int stockPrice,
    int stockQuantity,
    int totalStockQuantity
) {}