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
            0,
            totalInvestment
            );
    }
    // 서비스 내부에서 조회하고 계산한 값들을 기반으로 Entity를 생성하기 때문에 값을 바로 받는 형태이다 


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
        return new PlayerStockListResponse(
                playerStock.getStock().getName(),
                playerStock.getStock().getPrice(),
                playerStock.getQuantity(),
                playerStock.getProfitRate(),
                playerStock.getTotalInvestment()
        );
    }

}
