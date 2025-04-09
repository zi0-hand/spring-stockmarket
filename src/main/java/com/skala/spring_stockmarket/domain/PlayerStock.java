package com.skala.spring_stockmarket.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_stocks")
@NoArgsConstructor
@Getter
public class PlayerStock {
    
    @Id
    @Column(name = "player_stock_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(nullable=false)
    private int quantity; // 보유 수량

    @Column(nullable=false)
    private int totalInvestment; // 총 투자금액 

    // profitRate 필드 제거

    // 생성자 수정
    public PlayerStock(UUID id, Player player, Stock stock, int quantity, int totalInvestment) {
        this.id = id;
        this.player = player;
        this.stock = stock;
        this.quantity = quantity;
        this.totalInvestment = totalInvestment;
    }

    //== 비즈니스 로직 ==//

    // 주식 추가 매수 
    public void addQuantity(int stockQuantity, int totalInvestment) {
        this.quantity += stockQuantity; // 수량 증가 
        this.totalInvestment += totalInvestment; // 투자금 증가 
    }

    // 주식 매도 
    public void subtractQuantity(int stockQuantity, int totalPrice) {
        this.quantity -= stockQuantity;
        this.totalInvestment -= totalPrice;
    }
    
    // 평균 매수가 계산 메서드 추가
    public double getAveragePurchasePrice() {
        if (quantity == 0) return 0;
        return (double) totalInvestment / quantity;
    }
}