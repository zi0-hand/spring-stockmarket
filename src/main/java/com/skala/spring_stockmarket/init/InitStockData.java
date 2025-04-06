package com.skala.spring_stockmarket.init; // 패키지 명시

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.dto.request.CreateStockRequest;
import com.skala.spring_stockmarket.service.StockService;

@Component
@RequiredArgsConstructor
public class InitStockData {

    private final StockService stockService;

    @PostConstruct
    public void init() {
        stockService.createStock(new CreateStockRequest("SK", 200, "대한민국 대표 기업"));
        stockService.createStock(new CreateStockRequest("네이버", 150, "대한민국 대표 포털사이트"));
        stockService.createStock(new CreateStockRequest("카카오", 100, "모바일 플랫폼 기업"));
    }
}
