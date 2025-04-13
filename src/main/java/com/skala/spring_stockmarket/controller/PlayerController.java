package com.skala.spring_stockmarket.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skala.spring_stockmarket.dto.request.AddMoneyRequest;
import com.skala.spring_stockmarket.dto.request.LoginRequest;
import com.skala.spring_stockmarket.dto.request.SignUpRequest;
import com.skala.spring_stockmarket.dto.response.PlayerDetailResponse;
import com.skala.spring_stockmarket.dto.response.PlayerResponse;
import com.skala.spring_stockmarket.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Player API", description="사용자 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    // 로그인 
    @Operation(summary="로그인", description="사용자가 로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<UUID> login(@RequestBody LoginRequest loginRequest) {
        UUID playerId = playerService.login(loginRequest); 
        return ResponseEntity.ok(playerId);              
    }

    // 회원가입
    @Operation(summary = "회원가입", description="회원가입 요청에 대해 새로운 사용자 데이터를 생성합니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<PlayerResponse>  signUp(@RequestBody SignUpRequest signUpRequest) {
        PlayerResponse response = playerService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 플레이어 1명 상세 조회
    @Operation(summary="사용자 상세 조회", description="사용자의 상세 정보(보유 주식, 최근 거래)를 조회합니다.")
    @GetMapping("/{playerId}/detail")
    public ResponseEntity<PlayerDetailResponse> getPlayerDetail(@PathVariable("playerId") UUID playerId) {
        PlayerDetailResponse response = playerService.getPlayerDetail(playerId);
        return ResponseEntity.ok(response);
    }

    // 플레이어 전체 목록 조회 
    @Operation(summary="플레이어 전체 목록 조회", description="모든 플레이어를 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getPlayerList(
        @RequestParam(value = "page", defaultValue = "0") int page) {
            Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
            List<PlayerResponse> response = playerService.getPlayerList(pageable);
            return ResponseEntity.ok(response);
    }
    

    @Operation(summary="플레이어 자금 조회", description = "플레이어 ID를 통해 사용자의 자본금을 조회합니다.")
    @GetMapping("/{playerId}/money")
    public ResponseEntity<Double> getPlayerMoney(@PathVariable("playerId") UUID playerId) {
        Double money = playerService.getPlayerMoney(playerId);
        return ResponseEntity.ok(money);
    }
    

    // 플레이어 자금 추가 
    @Operation(summary="플레이어 자금 추가", description = "자금 추가 요청으로부터 사용자의 자본금을 추가합니다.")
    @PutMapping("/{playerId}/money")
    public ResponseEntity<PlayerResponse> addPlayerMoney(@PathVariable("playerId") UUID playerId, @RequestBody AddMoneyRequest request) {
        PlayerResponse response = playerService.addPlayerMoney(playerId, request);
        return ResponseEntity.ok(response);
    }

}