package com.skala.spring_stockmarket.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockPriceHistoryResponse(
    UUID id,
    String stockName,
    int price,
    LocalDateTime timestamp
) {}