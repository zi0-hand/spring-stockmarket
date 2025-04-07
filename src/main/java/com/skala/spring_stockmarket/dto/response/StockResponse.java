// StockResponse.java
package com.skala.spring_stockmarket.dto.response;

import java.util.UUID;

public record StockResponse(
    UUID id,
    String stockName,
    int stockPrice
) {}