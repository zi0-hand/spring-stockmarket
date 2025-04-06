package com.skala.spring_stockmarket.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skala.spring_stockmarket.domain.PlayerStock;

@Repository
public interface PlayerStockRepository extends JpaRepository<PlayerStock, UUID> {
    
}