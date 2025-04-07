package com.skala.spring_stockmarket.init;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.dto.request.SignUpRequest;
import com.skala.spring_stockmarket.service.PlayerService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitPlayerData {

    private final PlayerService playerService;

    @PostConstruct
    public void init() {
        // 이미 존재하는 사용자가 있는 경우 예외가 발생할 수 있으므로 try-catch로 처리
        try {
            playerService.signUp(new SignUpRequest("1", "1", 10000));
            playerService.signUp(new SignUpRequest("2", "2", 20000));
            playerService.signUp(new SignUpRequest("3", "3", 30000));
        } catch (Exception e) {
            // 이미 사용자가 존재하는 경우 무시
            System.out.println("일부 사용자가 이미 존재합니다: " + e.getMessage());
        }
    }
}