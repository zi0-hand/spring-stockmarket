package com.skala.spring_stockmarket.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skala.spring_stockmarket.domain.Stock;
import com.skala.spring_stockmarket.dto.request.CreateStockRequest;
import com.skala.spring_stockmarket.dto.response.StockResponse;
import com.skala.spring_stockmarket.exception.CustomException;
import com.skala.spring_stockmarket.mapper.StockMapper;
import com.skala.spring_stockmarket.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {
    
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public Stock findById(final UUID stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new CustomException("해당 주식이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    public Stock findByName(final String stockName) {
        return stockRepository.findByName(stockName)
                .orElseThrow(() -> new CustomException("해당 주식이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    
    // 전체 주식 목록 출력
    public List<StockResponse> getCurrentStockList(Pageable pageable) {
        Page<Stock> marketStockList = stockRepository.findAll(pageable);
        return marketStockList.stream().map(stockMapper::toResponse).toList();
    }


    // 주식 상장 
    @Transactional // 쓰기 가능 트랜잭션으로 재정의 
    public StockResponse createStock(CreateStockRequest request) {
        if (stockRepository.existsByName(request.getName())) {
            throw new CustomException("이미 존재하는 주식입니다.", HttpStatus.CONFLICT);
        }

        Stock stock = stockMapper.toEntity(request); // 요청 DTO를 실제 저장 가능한 Stock 엔티티로 변환
        stock = stockRepository.save(stock); // DB에 저장된 결과를 반영한 영속 상태의 엔티티를 반환

        return stockMapper.toResponse(stock);
    }

    // 랜덤 비율로 주식 가격 변경 
    @Transactional
    public int changePrice(final UUID stockId) {
        Stock stock = findById(stockId);

        int changedPrice = calculateFluctuation(stock.getPrice());
        stock.changePrice(changedPrice); // 이때의 changePrice는 내가 stock 도메인 클래스를 만들때 설정했던 메서드 이름 

        return changedPrice;
    }


    // 랜덤값 생성 
    private int calculateFluctuation(int price) {
        // -10% ~ +10% 범위의 랜덤 비율 생성
        double percentage = (Math.random() * 20) - 10;
        double fluctuation = price * (percentage / 100);
        return (int) Math.round(price + fluctuation); // 소수점 반올림 후 정수로 변환
    }

}
