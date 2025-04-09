package com.skala.spring_stockmarket.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlayerStockHistoryResponse(
    UUID id,
    String stockName,
    String transactionType,
    int quantity,
    int price,
    int totalAmount,
    int profitRate,  
    LocalDateTime timestamp
) {}