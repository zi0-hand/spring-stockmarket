// PlayerStockHistoryService.java (새 파일 생성)
package com.skala.spring_stockmarket.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skala.spring_stockmarket.domain.PlayerStockHistory;
import com.skala.spring_stockmarket.dto.response.PlayerStockHistoryResponse;
import com.skala.spring_stockmarket.mapper.PlayerStockHistoryMapper;
import com.skala.spring_stockmarket.repository.PlayerStockHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerStockHistoryService {
    
    private final PlayerStockHistoryRepository playerStockHistoryRepository;
    private final PlayerStockHistoryMapper playerStockHistoryMapper;
    
    // 플레이어별 거래 내역 조회
    public List<PlayerStockHistoryResponse> getPlayerStockHistories(UUID playerId) {
        // 추가해야 할 메서드: findByPlayerId
        List<PlayerStockHistory> histories = playerStockHistoryRepository.findByPlayer_Id(playerId, 
            Sort.by(Sort.Direction.DESC, "timestamp"));
        
        return histories.stream()
            .map(playerStockHistoryMapper::toResponse)
            .toList();
    }
}