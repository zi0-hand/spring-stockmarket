package com.skala.spring_stockmarket.dto.mapper;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.Player;
import com.skala.spring_stockmarket.dto.request.SignUpRequest;
import com.skala.spring_stockmarket.dto.response.PlayerResponse;

@Component
public class PlayerMapper {

    public Player signUpToEntity(final SignUpRequest signUpRequest) {
        return new Player(
            UUID.randomUUID(), 
            signUpRequest.getNickname(), 
            signUpRequest.getPassword(), 
            signUpRequest.getMoney(), 
            new ArrayList<>(), // 보유 주식 목록
            new ArrayList<>() // 거래 내역 목록 
        );
    } 

    public PlayerResponse toResponse(final Player player) {
        return new PlayerResponse(
            player.getId(), 
            player.getNickname(), 
            player.getMoney()
        );
    }
}
