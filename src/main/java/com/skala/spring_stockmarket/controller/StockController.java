package com.skala.spring_stockmarket.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skala.spring_stockmarket.dto.request.CreateStockRequest;
import com.skala.spring_stockmarket.dto.response.StockResponse;
import com.skala.spring_stockmarket.service.StockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Stock API", description = "주식 API입니다.") // Swagger,OpenAPI 문서 자동화용 어노테이션
@RestController // Rest API 컨트롤러 
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // 전체 주식 목록 출력 
    @Operation(summary = "전체 주식 목록 확인", description = "시장에 나와있는 전체 주식 목록을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<StockResponse>> getCurrentStockList(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "id"));
        List<StockResponse> response = stockService.getCurrentStockList(pageable);

        return ResponseEntity.ok(response);
    }

    // 주식 상장
    @Operation(summary = "주식 상장", description = "새로운 주식을 생성합니다.")
    @PostMapping("")
    public ResponseEntity<StockResponse> addStock(@RequestBody CreateStockRequest request) {
        StockResponse response = stockService.createStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 주식 가격 변경
    @Operation(summary = "랜덤 주식 가격 변경", description = "랜덤 비율로 주식 가격을 변경합니다.")
    @PutMapping("/{stockId}")
    public ResponseEntity<String> addStock(@PathVariable("stockId") UUID stockId) { // @URIPathVariable:  경로에 있는 변수 값을 메서드 파라미터로 연결
        int changedStockPrice = stockService.changePrice(stockId);
        return ResponseEntity.ok(changedStockPrice + "로 주식 가격이 변경되었습니다.");
    }


    
    /**
     * 주식 상장 폐지
     */
//    @Operation(summary = "주식 상장 폐지", description = "요청된 주식을 삭제합니다. 회원이 보유한 주식 목록에서도 삭제합니다.")
//    @DeleteMapping("")
//    public ResponseEntity<String> deleteStock() {
//        return ResponseEntity.ok("");
//    }
}


