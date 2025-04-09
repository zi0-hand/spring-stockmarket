package com.skala.spring_stockmarket.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skala.spring_stockmarket.domain.Player;
import com.skala.spring_stockmarket.dto.response.PortfolioAnalysisResponse;
import com.skala.spring_stockmarket.dto.response.PortfolioAnalysisResponse.AssetAllocationItem;
import com.skala.spring_stockmarket.dto.response.PortfolioAnalysisResponse.StockPerformanceItem;
import com.skala.spring_stockmarket.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioAnalysisService {

    private final PlayerRepository playerRepository;

    public PortfolioAnalysisResponse analyzePortfolio(UUID playerId) {
        Player player = playerRepository.findByIdWithStocks(playerId)
            .orElseThrow(() -> new RuntimeException("플레이어를 찾을 수 없습니다."));

        // 총 투자 금액 계산
        BigDecimal totalInvestment = calculateTotalInvestment(player);

        // 현재 포트폴리오 가치 계산
        BigDecimal currentPortfolioValue = calculateCurrentPortfolioValue(player);

        // 총 손익 계산
        BigDecimal totalProfitLoss = currentPortfolioValue.subtract(totalInvestment);

        // 전체 수익률 계산
        BigDecimal overallProfitRate = calculateOverallProfitRate(totalInvestment, currentPortfolioValue);

        // 자산 배분 계산
        List<AssetAllocationItem> assetAllocation = calculateAssetAllocation(player, currentPortfolioValue);

        // 개별 주식 성과 계산
        List<StockPerformanceItem> stockPerformance = calculateStockPerformance(player);

        return new PortfolioAnalysisResponse(
            totalInvestment,
            currentPortfolioValue,
            totalProfitLoss,
            overallProfitRate,
            assetAllocation,
            stockPerformance
        );
    }

    private BigDecimal calculateTotalInvestment(Player player) {
        return player.getPlayerStockList().stream()
            .map(ps -> BigDecimal.valueOf(ps.getTotalInvestment()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateCurrentPortfolioValue(Player player) {
        return player.getPlayerStockList().stream()
            .map(ps -> BigDecimal.valueOf(ps.getStock().getPrice() * ps.getQuantity()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateOverallProfitRate(BigDecimal totalInvestment, BigDecimal currentPortfolioValue) {
        if (totalInvestment.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return currentPortfolioValue.subtract(totalInvestment)
            .divide(totalInvestment, 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100));
    }

    private List<AssetAllocationItem> calculateAssetAllocation(Player player, BigDecimal totalPortfolioValue) {
        return player.getPlayerStockList().stream()
            .map(ps -> {
                BigDecimal stockValue = BigDecimal.valueOf(ps.getStock().getPrice() * ps.getQuantity());
                BigDecimal percentage = totalPortfolioValue.compareTo(BigDecimal.ZERO) == 0 ? 
                    BigDecimal.ZERO : 
                    stockValue.divide(totalPortfolioValue, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                
                return new AssetAllocationItem(
                    ps.getStock().getName(),
                    percentage,
                    stockValue
                );
            })
            .sorted((a, b) -> b.percentage().compareTo(a.percentage()))
            .collect(Collectors.toList());
    }

    private List<StockPerformanceItem> calculateStockPerformance(Player player) {
        return player.getPlayerStockList().stream()
            .map(ps -> {
                BigDecimal purchasePrice = BigDecimal.valueOf(ps.getTotalInvestment())
                    .divide(BigDecimal.valueOf(ps.getQuantity()), 2, RoundingMode.HALF_UP);
                
                BigDecimal currentStockValue = BigDecimal.valueOf(ps.getStock().getPrice() * ps.getQuantity());
                BigDecimal totalPurchaseValue = BigDecimal.valueOf(ps.getTotalInvestment());
                
                BigDecimal profitLoss = currentStockValue.subtract(totalPurchaseValue);
                
                BigDecimal profitRate = totalPurchaseValue.compareTo(BigDecimal.ZERO) == 0 ? 
                    BigDecimal.ZERO : 
                    profitLoss.divide(totalPurchaseValue, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                
                return new StockPerformanceItem(
                    ps.getStock().getName(),
                    ps.getStock().getPrice(),
                    ps.getQuantity(),
                    purchasePrice,
                    profitLoss,
                    profitRate
                );
            })
            .collect(Collectors.toList());
    }
}