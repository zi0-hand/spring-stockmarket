package com.skala.spring_stockmarket.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skala.spring_stockmarket.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    // 내가 새로 선언한 파생 쿼리 메서드 (이거는 생략하면 안됨)
    Optional<Stock> findByName(String name); // 주식 이름으로 주식 검색 (Optional: 반환 결과가 없어도 ok)
    boolean existsByName(String name); // 특정 이름의 주식이 있는지 확인  
}
