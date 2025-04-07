// PlayerStockHistoryRepository.java 
package com.skala.spring_stockmarket.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skala.spring_stockmarket.domain.PlayerStockHistory;

@Repository
public interface PlayerStockHistoryRepository extends JpaRepository<PlayerStockHistory, UUID> {
    // 플레이어 ID로 거래 내역 조회 - 정렬 옵션 추가
    // List<PlayerStockHistory> findByPlayerId(UUID playerId, Sort sort);
    List<PlayerStockHistory> findByPlayer_Id(UUID playerId, Sort sort);
}