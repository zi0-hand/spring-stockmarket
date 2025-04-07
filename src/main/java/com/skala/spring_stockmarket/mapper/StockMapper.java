package com.skala.spring_stockmarket.mapper;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.Stock;
import com.skala.spring_stockmarket.dto.request.CreateStockRequest;
import com.skala.spring_stockmarket.dto.response.StockResponse;

@Component // 아래 클래스를 spring bean으로 등록하기 위한 어노테이션 (@Autowired나 생성자 주입을 통해 사용 가능)
public class StockMapper {

    public Stock toEntity(final CreateStockRequest request) {
        return new Stock(
            UUID.randomUUID(), 
            request.name(), 
            request.price(), 
            request.description(), 
            new ArrayList<>()
        );
    }

    // 엔티티를 dto로 변환 
    public StockResponse toResponse(final Stock stock) {
        return new StockResponse(
            stock.getId(),
            stock.getName(), 
            stock.getPrice()
        );
    }


}