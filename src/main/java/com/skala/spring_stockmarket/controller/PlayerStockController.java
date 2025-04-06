package com.skala.spring_stockmarket.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skala.spring_stockmarket.dto.request.BuyPlayerStockRequest;
import com.skala.spring_stockmarket.dto.request.SellPlayerStockRequest;
import com.skala.spring_stockmarket.dto.response.PlayerStockListResponse;
import com.skala.spring_stockmarket.dto.response.PlayerStockResponse;
import com.skala.spring_stockmarket.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Player Stock API", description = "사용자 보유 주식 관리 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players/player-stocks")
public class PlayerStockController {

    private final PlayerService playerService;

    // 보유 주식 목록 확인
    @Operation(summary="보유 주식 목록 확인", description="사용자의 보유 주식 목록을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<PlayerStockListResponse>> getPlayerStockList(@RequestParam("playerId") UUID playerId) {
        List<PlayerStockListResponse> response = playerService.getPlayerStockList(playerId);
        return ResponseEntity.ok(response);
    }

    /**
     * 주식 매수
     */
    @Operation(summary = "주식 매수", description = "사용자의 주식 매수 요청을 처리합니다.")
    @PostMapping("")
    public ResponseEntity<PlayerStockResponse> addPlayerStock(@RequestBody BuyPlayerStockRequest request) {
        PlayerStockResponse response = playerService.buyStock(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 주식 매도
     */
    @Operation(summary = "주식 매도", description = "사용자의 주식 매도 요청을 처리합니다.")
    @PutMapping("")
    public ResponseEntity<PlayerStockResponse> sellPlayerStock(@RequestBody SellPlayerStockRequest request) {
        PlayerStockResponse response = playerService.sellPlayerStock(request);
        return ResponseEntity.ok(response);
    }

    
}
