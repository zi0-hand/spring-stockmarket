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
@Table(name = "stocks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Stock {

    @Id
    @Column(name = "stock_id")
    private UUID id; // 고유 식별자 
    // 모든 엔티티에서 기본키를 id로 명명하면 코드를 읽기 더 쉬워짐 

    @Column(nullable=false)
    private String name; 

    @Column(nullable=false)
    private int price;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy="stock", cascade = CascadeType.ALL)
    private List<StockPriceHistory> priceHistories; // 주식 이력을 담는 컬렉션 (실제 데이터베이스에 생성되지 않고, 그냥 관계 맺음을 보여줌)
    // 외래키를 실제로 갖고 있는 쪽이 관계의 주인이 됨 
    // cascade = CascadeType.ALL: 연결된 모든 작업에 자동 적용 


    //== 비즈니스 로직 ==//
    // 가격 변동 로직 
    public void changePrice(int price) {
        this.price = price;
    }
    // 데이터를 변경하는 것이 같은 클래스 내에 있는 것이 캡슐화 원칙에 부합함 
    
}
