package com.skala.spring_stockmarket.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skala.spring_stockmarket.dto.response.PortfolioAnalysisResponse;
import com.skala.spring_stockmarket.service.PortfolioAnalysisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Portfolio Analysis API", description = "투자 포트폴리오 분석 API")
@RestController
@RequestMapping("/api/players/{playerId}/portfolio")
@RequiredArgsConstructor
public class PortfolioAnalysisController {

    private final PortfolioAnalysisService portfolioAnalysisService;

    @Operation(summary = "포트폴리오 분석", description = "사용자의 투자 포트폴리오를 종합적으로 분석합니다.")
    @GetMapping("/analysis")
    public ResponseEntity<PortfolioAnalysisResponse> analyzePortfolio(
            @PathVariable("playerId") UUID playerId) {
        PortfolioAnalysisResponse analysis = portfolioAnalysisService.analyzePortfolio(playerId);
        return ResponseEntity.ok(analysis);
    }
}