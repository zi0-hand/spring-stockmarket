package com.skala.spring_stockmarket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerStockService {

    // 주식 가격 계산 
    public int calculation(int stockPrice, int stockQuantity) {
        return stockPrice * stockQuantity;
    }
    
}
