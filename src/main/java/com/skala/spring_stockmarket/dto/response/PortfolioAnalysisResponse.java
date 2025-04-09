package com.skala.spring_stockmarket.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioAnalysisResponse(
    BigDecimal totalInvestment,           // 총 투자 금액
    BigDecimal currentPortfolioValue,     // 현재 포트폴리오 가치
    BigDecimal totalProfitLoss,           // 총 손익
    BigDecimal overallProfitRate,         // 전체 수익률
    List<AssetAllocationItem> assetAllocation, // 자산 배분
    List<StockPerformanceItem> stockPerformance // 개별 주식 성과
) {
    public record AssetAllocationItem(
        String stockName,
        BigDecimal percentage,
        BigDecimal value
    ) {}

    public record StockPerformanceItem(
        String stockName,
        int currentPrice,
        int quantity,
        BigDecimal purchasePrice,
        BigDecimal profitLoss,
        BigDecimal profitRate
    ) {}
}