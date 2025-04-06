package com.skala.spring_stockmarket.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_stock_histories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayerStockHistory {

    @Id
    @Column(name = "player_stock_history_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(nullable=false)
    private String transactionType;

    @Column(nullable=false)
    private int quantity;

    @Column(nullable=false)
    private int price;

    @Column(nullable = false)
    private int totalAmount; // 총 양 = 구매량 * 구매 가격 

    @Column(nullable = false)
    private LocalDateTime timestamp;
    
}
