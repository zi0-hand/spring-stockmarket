package com.skala.spring_stockmarket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skala.spring_stockmarket.domain.Stock;
import com.skala.spring_stockmarket.domain.StockPriceHistory;
import com.skala.spring_stockmarket.dto.response.StockPriceHistoryResponse;
import com.skala.spring_stockmarket.mapper.StockPriceHistoryMapper;
import com.skala.spring_stockmarket.repository.StockPriceHistoryRepository;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockPriceHistoryService {
    
    private final StockPriceHistoryRepository stockPriceHistoryRepository;
    private final StockPriceHistoryMapper stockPriceHistoryMapper;

    @Transactional
    public void saveStockPriceHistory(Stock stock, int price) {
        StockPriceHistory priceHistory = new StockPriceHistory(
            UUID.randomUUID(), 
            stock, 
            price, 
            LocalDateTime.now()
        );
        stockPriceHistoryRepository.save(priceHistory);
    }

    public List<StockPriceHistoryResponse> getStockPriceHistories(UUID stockId) {
        List<StockPriceHistory> histories = stockPriceHistoryRepository.findByStock_Id(stockId, 
            Sort.by(Sort.Direction.DESC, "timestamp"));
        
        return histories.stream()
            .map(stockPriceHistoryMapper::toResponse)
            .toList();
    }
}
