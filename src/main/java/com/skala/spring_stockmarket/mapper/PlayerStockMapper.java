package com.skala.spring_stockmarket.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.Player;
import com.skala.spring_stockmarket.domain.PlayerStock;
import com.skala.spring_stockmarket.domain.Stock;
import com.skala.spring_stockmarket.dto.response.PlayerStockListResponse;
import com.skala.spring_stockmarket.dto.response.PlayerStockResponse;

@Component
public class PlayerStockMapper {

    public PlayerStock toEntity(final Player player, final Stock stock, final int stockQuantity, final int totalInvestment) {
        return new PlayerStock(
            UUID.randomUUID(), 
            player, 
            stock, 
            stockQuantity, 
            totalInvestment
            );
    }

    public PlayerStockResponse toResponse(final Player player, final int stockQuantity, final PlayerStock playerStock) {
        return new PlayerStockResponse(
                player.getNickname(),
                player.getMoney(),
                playerStock.getStock().getName(),
                playerStock.getStock().getPrice(),
                stockQuantity,
                playerStock.getQuantity()
        );
    }

    public PlayerStockListResponse toResponseForList(final PlayerStock playerStock) {
        // 수익률을 계산하지만 엔티티에 저장하지는 않음
        double avgPurchasePrice = playerStock.getAveragePurchasePrice();
        int currentPrice = playerStock.getStock().getPrice();
        int profitRate = avgPurchasePrice > 0 ? 
            (int) Math.round((currentPrice - avgPurchasePrice) / avgPurchasePrice * 100) : 0;
            
        return new PlayerStockListResponse(
            playerStock.getStock().getId(),
            playerStock.getStock().getName(),
            currentPrice,
            playerStock.getQuantity(),
            profitRate, // 실시간 계산된 수익률
            playerStock.getTotalInvestment(),
            avgPurchasePrice // 추가: 평균 매수가
        );
    }
}