package com.skala.spring_stockmarket.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.Player;
import com.skala.spring_stockmarket.dto.request.SignUpRequest;
import com.skala.spring_stockmarket.dto.response.PlayerDetailResponse;
import com.skala.spring_stockmarket.dto.response.PlayerResponse;
import com.skala.spring_stockmarket.dto.response.PlayerStockHistoryResponse;
import com.skala.spring_stockmarket.dto.response.PlayerStockListResponse;

@Component
public class PlayerMapper {

    public Player signUpToEntity(final SignUpRequest signUpRequest) {
        return new Player(
            UUID.randomUUID(), 
            signUpRequest.nickname(), 
            signUpRequest.password(), 
            signUpRequest.money(), 
            new ArrayList<>(),
            new ArrayList<>()
        );
    } 

    public PlayerResponse toResponse(final Player player) {
        return new PlayerResponse(
            player.getId(), 
            player.getNickname(), 
            player.getMoney()
        );
    }

    public PlayerDetailResponse toDetailResponse(
        final Player player, 
        List<PlayerStockListResponse> stockList,
        List<PlayerStockHistoryResponse> recentTransactions) {
        
        return new PlayerDetailResponse(
            player.getId(),
            player.getNickname(),
            player.getMoney(),
            stockList,
            recentTransactions
        );
    }

}