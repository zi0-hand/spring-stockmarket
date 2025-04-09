package com.skala.spring_stockmarket.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.Stock;

@Component
public class AdvancedStockPriceSimulator {
    private Map<UUID, StockTrend> stockTrends = new ConcurrentHashMap<>();

    private static class StockTrend {
        private double momentum; // 현재 추세
        private double volatility; // 변동성

        public StockTrend() {
            // 초기 추세와 변동성 랜덤 설정
            this.momentum = (Math.random() - 0.5) * 0.2;
            this.volatility = Math.random() * 0.1 + 0.05; // 5~15% 변동성
        }
        
        public int calculateNextPrice(int currentPrice) {
            // 추세 변화 조정
            momentum += (Math.random() - 0.5) * 0.1;
            momentum = Math.max(-0.5, Math.min(momentum, 0.5)); // 추세 제한
            
            // 변동성 자연스러운 변화
            volatility += (Math.random() - 0.5) * 0.02;
            volatility = Math.max(0.03, Math.min(volatility, 0.2)); // 변동성 범위 제한
            
            // 추세와 랜덤성을 결합한 가격 변동
            double priceChange = momentum + (Math.random() - 0.5) * volatility;
            
            return (int) Math.round(currentPrice * (1 + priceChange));
        }
    }

    public int simulateStockPrice(Stock stock) {
        StockTrend trend = stockTrends.computeIfAbsent(
            stock.getId(), 
            k -> new StockTrend()
        );
        
        return trend.calculateNextPrice(stock.getPrice());
    }
}