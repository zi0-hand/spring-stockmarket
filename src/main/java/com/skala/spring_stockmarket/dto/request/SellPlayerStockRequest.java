// SellPlayerStockRequest.java
package com.skala.spring_stockmarket.dto.request;

import java.util.UUID;

public record SellPlayerStockRequest(
    UUID playerId,
    UUID stockId,
    int stockQuantity
) {}