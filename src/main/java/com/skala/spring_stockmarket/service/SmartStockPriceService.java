package com.skala.spring_stockmarket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skala.spring_stockmarket.domain.Stock;
import com.skala.spring_stockmarket.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmartStockPriceService {
    private final AdvancedStockPriceSimulator priceSimulator;
    private final NewsBasedStockPriceUpdater newsUpdater;
    private final StockPriceHistoryService stockPriceHistoryService;
    private final StockRepository stockRepository;

    // @Scheduled(fixedRate = 1000) // 1초마다 실행
    @Scheduled(fixedRate = 30000) // 30초마다 실행
    // @Scheduled(fixedRate = 300000) // 5분마다 실행
    @Transactional
    public void updateAllStockPrices() {
        log.info("주식 가격 자동 업데이트 시작 - {}", LocalDateTime.now());
        List<Stock> stocks = stockRepository.findAll();
        
        stocks.forEach(stock -> {
            int newPrice = updateSingleStockPrice(stock);
            log.info("주식 {} 가격 업데이트: {} -> {}", 
                stock.getName(), 
                stock.getPrice(), 
                newPrice
            );
        });
        
        log.info("주식 가격 자동 업데이트 완료 - {}", LocalDateTime.now());
    }

    public int updateSingleStockPrice(Stock stock) {
        // 시뮬레이션 기반 가격
        int simulatedPrice = priceSimulator.simulateStockPrice(stock);
        
        // 뉴스 기반 가격 조정
        int newsAdjustedPrice = newsUpdater.updateStockPriceBasedOnNews(stock);
        
        // 두 방식의 평균 가격
        int newPrice = (simulatedPrice + newsAdjustedPrice) / 2;
        
        // 가격 변경
        stock.changePrice(newPrice);
        
        // 가격 변동 이력 저장
        stockPriceHistoryService.saveStockPriceHistory(stock, newPrice);
        
        return newPrice;
    }

    // 선택적: 특정 주식의 최근 뉴스 이벤트 로그
    public String getRecentNewsForStock(Stock stock) {
        boolean isPositive = Math.random() < 0.5;
        return newsUpdater.getRandomNewsEvent(isPositive);
    }
}