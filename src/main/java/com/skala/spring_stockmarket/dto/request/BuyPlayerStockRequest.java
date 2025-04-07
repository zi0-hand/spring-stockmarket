// BuyPlayerStockRequest.java
package com.skala.spring_stockmarket.dto.request;

import java.util.UUID;

public record BuyPlayerStockRequest(
    UUID playerId,
    UUID stockId,
    int stockQuantity
) {}