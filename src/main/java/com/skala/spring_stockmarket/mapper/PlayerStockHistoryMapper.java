package com.skala.spring_stockmarket.mapper;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.PlayerStockHistory;
import com.skala.spring_stockmarket.dto.response.PlayerStockHistoryResponse;

@Component
public class PlayerStockHistoryMapper {
    
    public PlayerStockHistoryResponse toResponse(final PlayerStockHistory history) {
        return new PlayerStockHistoryResponse(
            history.getId(),
            history.getStock().getName(),
            history.getTransactionType(),
            history.getQuantity(),
            history.getPrice(),
            history.getTotalAmount(),
            history.getProfitRate(),  // 수익률 추가
            history.getTimestamp()
        );
    }
}