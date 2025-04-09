package com.skala.spring_stockmarket.dto.response;

import java.util.UUID;

public record PlayerStockListResponse(
    UUID stockId,
    String stockName,
    int stockPrice,
    int stockQuantity,
    int profitRate, // 실시간 계산된 수익률
    int totalInvestment,
    double averagePurchasePrice // 추가: 평균 매수가
) {}