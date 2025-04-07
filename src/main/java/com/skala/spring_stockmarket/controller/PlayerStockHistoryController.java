// PlayerStockHistoryController.java 
package com.skala.spring_stockmarket.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skala.spring_stockmarket.dto.response.PlayerStockHistoryResponse;
import com.skala.spring_stockmarket.service.PlayerStockHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Player Stock History API", description = "사용자 주식 거래 내역 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players/player-stock-histories")
public class PlayerStockHistoryController {
    
    private final PlayerStockHistoryService playerStockHistoryService;
    
    @Operation(summary = "주식 거래 내역 조회", description = "사용자의 주식 거래 내역을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<PlayerStockHistoryResponse>> getPlayerStockHistories(
            @RequestParam("playerId") UUID playerId) {
        List<PlayerStockHistoryResponse> response = playerStockHistoryService.getPlayerStockHistories(playerId);
        return ResponseEntity.ok(response);
    }
}