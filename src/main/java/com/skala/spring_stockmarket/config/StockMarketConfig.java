package com.skala.spring_stockmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.skala.spring_stockmarket.repository.StockRepository;
import com.skala.spring_stockmarket.service.AdvancedStockPriceSimulator;
import com.skala.spring_stockmarket.service.NewsBasedStockPriceUpdater;
import com.skala.spring_stockmarket.service.SmartStockPriceService;
import com.skala.spring_stockmarket.service.StockPriceHistoryService;

@Configuration
public class StockMarketConfig {
    @Bean
    public SmartStockPriceService smartStockPriceService(
        AdvancedStockPriceSimulator priceSimulator,
        NewsBasedStockPriceUpdater newsUpdater,
        StockPriceHistoryService stockPriceHistoryService,
        StockRepository stockRepository
    ) {
        return new SmartStockPriceService(
            priceSimulator, 
            newsUpdater, 
            stockPriceHistoryService, 
            stockRepository
        );
    }
}