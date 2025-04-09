package com.skala.spring_stockmarket.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.skala.spring_stockmarket.domain.Stock;

@Component
public class NewsBasedStockPriceUpdater {
    private final List<String> positiveNews = Arrays.asList(
        "회사 분기 실적 호조", 
        "신제품 출시 성공", 
        "전략적 파트너십 체결",
        "지속 가능한 성장 전략 발표",
        "국제 시장 진출 성공"
    );
    
    private final List<String> negativeNews = Arrays.asList(
        "규제 리스크 증가", 
        "경영진 변동", 
        "시장 점유율 감소",
        "예상치 못한 손실 발생",
        "글로벌 경제 불확실성"
    );

    public int updateStockPriceBasedOnNews(Stock stock) {
        int currentPrice = stock.getPrice();
        
        // 뉴스 이벤트 랜덤 선택
        double newsEventProbability = Math.random();
        
        double impactFactor;
        if (newsEventProbability < 0.3) {
            // 긍정적 뉴스
            impactFactor = Math.random() * 0.02 + 0.005; // 0.5~2.5% 상승/하락
            return (int) (currentPrice * (1 + impactFactor));
        } else if (newsEventProbability < 0.6) {
            // 부정적 뉴스
            impactFactor = Math.random() * 0.02 + 0.005; // 0.5~2.5% 상승/하락
            return (int) (currentPrice * (1 - impactFactor));
        }
        
        // 60% 확률로 별다른 변동 없음
        return currentPrice;
    }

    // 뉴스 이벤트 로그 (선택적)
    public String getRandomNewsEvent(boolean isPositive) {
        List<String> newsList = isPositive ? positiveNews : negativeNews;
        return newsList.get((int)(Math.random() * newsList.size()));
    }
}