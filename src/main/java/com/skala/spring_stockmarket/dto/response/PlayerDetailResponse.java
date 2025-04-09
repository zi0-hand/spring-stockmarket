package com.skala.spring_stockmarket.dto.response;

import java.util.List;
import java.util.UUID;

public record PlayerDetailResponse(
    UUID id,
    String nickname,
    int money,
    List<PlayerStockListResponse> stockList,
    List<PlayerStockHistoryResponse> recentTransactions
) {}