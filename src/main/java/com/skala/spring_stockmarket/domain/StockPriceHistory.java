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
@Table(name = "stock_price_histories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockPriceHistory {
    
    @Id
    @Column(name = "stock_price_history_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY) // fetch = FetchType.LAZY: 지연 로딩을 의미 (= 이 객체를 조회할 때 stock 객체는 즉시 로딩되지 않고, 실제로 필드에 접근할 때만 데이터베이스에서 로딩됨)
    @JoinColumn(name = "stock_id")
    private Stock stock; // 필드를 객체 타입으로 선언 

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDateTime timestamp;
    
}
