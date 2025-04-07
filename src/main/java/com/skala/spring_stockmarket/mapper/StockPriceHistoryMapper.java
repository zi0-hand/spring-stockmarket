package com.skala.spring_stockmarket.mapper;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.StockPriceHistory;
import com.skala.spring_stockmarket.dto.response.StockPriceHistoryResponse;

@Component
public class StockPriceHistoryMapper {
    
    public StockPriceHistoryResponse toResponse(final StockPriceHistory history) {
        return new StockPriceHistoryResponse(
            history.getId(),
            history.getStock().getName(),
            history.getPrice(),
            history.getTimestamp()
        );
    }
}