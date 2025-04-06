package com.skala.spring_stockmarket.domain;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Player {

    @Id
    @Column(name = "player_id")
    private UUID id;

    @Column(nullable = false, unique = true) // 이런거 예외는 데이터베이스에 저장하려고하면 발생
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int money;

    // 플레이어가 보유한 주식 리스트 
    @OneToMany(mappedBy="player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerStock> playerStockList;
    // orphanRemoval = true: 플레이어와의 관계가 끊긴 자식 엔티티는 자동으로 DB에서 삭제됨
    // player: 부모, PlayerStock: 자식 
    // player가 삭제되면 DB의 playerstock에서도 삭제된다 

    @OneToMany(mappedBy="player", cascade = CascadeType.ALL)
    private List<PlayerStockHistory> stockHistories;
    // stockHistories는 이력 보존이 목적이므로 orphanRemoval 사용하지 않음


    //== 비즈니스 로직 ==//
    public void subtractMoney(int totalPrice) {
        this.money -= totalPrice;
    }

    public void addMoney(int money) {
        this.money += money;
    }

}
