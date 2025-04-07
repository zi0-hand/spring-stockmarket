// CreateStockRequest.java
package com.skala.spring_stockmarket.dto.request;

public record CreateStockRequest(
    String name,
    int price,
    String description
) {}