// PlayerStockListResponse.java
package com.skala.spring_stockmarket.dto.response;

import java.util.UUID;

public record PlayerStockListResponse(
    UUID stockId,
    String stockName,
    int stockPrice,
    int stockQuantity,
    int profitRate,
    int totalInvestment
) {}