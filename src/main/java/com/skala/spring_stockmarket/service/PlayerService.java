package com.skala.spring_stockmarket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skala.spring_stockmarket.domain.Player;
import com.skala.spring_stockmarket.domain.PlayerStock;
import com.skala.spring_stockmarket.domain.PlayerStockHistory;
import com.skala.spring_stockmarket.domain.Stock;
import com.skala.spring_stockmarket.dto.request.AddMoneyRequest;
import com.skala.spring_stockmarket.dto.request.BuyPlayerStockRequest;
import com.skala.spring_stockmarket.dto.request.LoginRequest;
import com.skala.spring_stockmarket.dto.request.SellPlayerStockRequest;
import com.skala.spring_stockmarket.dto.request.SignUpRequest;
import com.skala.spring_stockmarket.dto.response.PlayerDetailResponse;
import com.skala.spring_stockmarket.dto.response.PlayerResponse;
import com.skala.spring_stockmarket.dto.response.PlayerStockHistoryResponse;
import com.skala.spring_stockmarket.dto.response.PlayerStockListResponse;
import com.skala.spring_stockmarket.dto.response.PlayerStockResponse;
import com.skala.spring_stockmarket.exception.CustomException;
import com.skala.spring_stockmarket.mapper.PlayerMapper;
import com.skala.spring_stockmarket.mapper.PlayerStockHistoryMapper;
import com.skala.spring_stockmarket.mapper.PlayerStockMapper;
import com.skala.spring_stockmarket.repository.PlayerRepository;
import com.skala.spring_stockmarket.repository.PlayerStockHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerMapper playerMapper;
    private final PlayerStockMapper playerStockMapper;
    private final PlayerStockHistoryMapper playerStockHistoryMapper;

    private final PlayerRepository playerRepository;
    private final PlayerStockHistoryRepository playerStockHistoryRepository;

    private final StockService stockService;
    private final PlayerStockService playerStockService;


    private Player findById(final UUID playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new CustomException("플레이어가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    private Player findByNickname(final String nickname) {
        return playerRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException("플레이어가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    private Player findByIdWithStocks(final UUID playerId) {
        return playerRepository.findByIdWithStocks(playerId)
        .orElseThrow(() -> new CustomException("플레이어가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    public Double getPlayerMoney(UUID playerId) {
        return playerRepository.findById(playerId)
            .map(player -> Double.valueOf(player.getMoney()))
            .orElseThrow(() -> new CustomException("플레이어가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }
    

    //플레이어의 보유 주식 목록에서 stock을 찾음
    private PlayerStock findPlayerStock(final Player player, final Stock stock) {
        return player.getPlayerStockList().stream() // List<PlayerStock>
                .filter(ps -> ps.getStock().getId().equals(stock.getId()))
                .findFirst() // 가장 첫 번째 것 하나만 가져와라
                .orElse(null);
    }

    // 로그인 
    public UUID login(final LoginRequest loginRequest) {
        Player player = findByNickname(loginRequest.nickname());
    
        if (!player.getPassword().equals(loginRequest.password())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.CONFLICT);
        }
    
        return player.getId(); 
    }

    // 회원가입
    @Transactional
    public PlayerResponse signUp(final SignUpRequest signUpRequest) {
        if (playerRepository.findByNickname(signUpRequest.nickname()).isPresent()) {
            throw new CustomException("이미 존재하는 닉네임입니다.", HttpStatus.CONFLICT);
        }

        Player player = playerMapper.signUpToEntity(signUpRequest);
        player = playerRepository.save(player);

        return playerMapper.toResponse(player);
    }

    // 특정 플레이어의 현재 주식 목록 확인 
    public List<PlayerStockListResponse> getPlayerStockList(UUID playerId) {
        Player player = findByIdWithStocks(playerId);
        
        if (player.getPlayerStockList().isEmpty()) {
            return null;
        }
        return player.getPlayerStockList().stream().map(playerStockMapper::toResponseForList).toList();
    }

    // 특정 플레이어의 상세 정보 조회 
    public PlayerDetailResponse getPlayerDetail(UUID playerId) {
        // 플레이어 정보 조회 (주식 목록 함께 로드)
        Player player = findByIdWithStocks(playerId);
        
        // 보유 주식 목록 매핑
        List<PlayerStockListResponse> stockList = player.getPlayerStockList().stream()
                .map(playerStockMapper::toResponseForList)
                .toList();
        
        // 최근 거래 이력 조회 (최근 10건만)
        List<PlayerStockHistory> histories = playerStockHistoryRepository
                .findByPlayer_Id(playerId, Sort.by(Sort.Direction.DESC, "timestamp"))
                .stream()
                .limit(10)
                .toList();
        
        List<PlayerStockHistoryResponse> recentTransactions = histories.stream()
                .map(playerStockHistoryMapper::toResponse)
                .toList();
        
        return playerMapper.toDetailResponse(player, stockList, recentTransactions);
    }

    @Transactional
    public PlayerStockResponse buyStock(BuyPlayerStockRequest request) {
        Player player = findByIdWithStocks(request.playerId());  // 주식 구매 플레이어
        Stock stock = stockService.findById(request.stockId());  // 구매 대상 주식
    
        int totalInvestment = playerStockService.calculation(stock.getPrice(), request.stockQuantity());
        if (player.getMoney() < totalInvestment) {
            throw new CustomException("잔액이 부족합니다.", HttpStatus.CONFLICT);
        }
    
        // 플레이어의 보유 주식 목록에서 해당 주식을 찾음
        PlayerStock playerStock = findPlayerStock(player, stock);
        
        if (playerStock == null) { // 새로 구매하는 경우
            playerStock = playerStockMapper.toEntity(player, stock, request.stockQuantity(), totalInvestment);
            player.getPlayerStockList().add(playerStock);
        } else {  // 기존 보유 주식 수량 증가
            playerStock.addQuantity(request.stockQuantity(), totalInvestment);
        }
    
        // 플레이어 보유 돈 차감
        player.subtractMoney(totalInvestment);
    
        // 거래 시점의 수익률 계산 (매수 시)
        double avgPurchasePrice = playerStock.getAveragePurchasePrice();
        int profitRate = avgPurchasePrice > 0 ? 
            (int) Math.round((stock.getPrice() - avgPurchasePrice) / avgPurchasePrice * 100) : 0;
    
        // 거래 이력 추가 (수익률 포함)
        PlayerStockHistory history = new PlayerStockHistory(
            UUID.randomUUID(),
            player,
            stock,
            "매수",
            request.stockQuantity(),
            stock.getPrice(),
            totalInvestment,
            profitRate,  // 거래 시점의 수익률
            LocalDateTime.now()
        );
        playerStockHistoryRepository.save(history);
    
        return playerStockMapper.toResponse(player, request.stockQuantity(), playerStock);
    }

    // 주식 매도 (판매)
    @Transactional
    public PlayerStockResponse sellPlayerStock(SellPlayerStockRequest request) {
        Player player = findByIdWithStocks(request.playerId());
        Stock stock = stockService.findById(request.stockId());
        
        PlayerStock playerStock = findPlayerStock(player, stock);
        if (playerStock == null) {
            throw new CustomException("보유 중인 주식이 아닙니다.", HttpStatus.NOT_FOUND);
        }
        if (playerStock.getQuantity() < request.stockQuantity()) {
            throw new CustomException("매도 수량이 보유 수량보다 많습니다.", HttpStatus.BAD_REQUEST);
        }
    
        int totalPrice = playerStockService.calculation(stock.getPrice(), request.stockQuantity());
        
        // 수익률 계산 (현재 가격과 평균 매수가 비교)
        double avgPurchasePrice = playerStock.getAveragePurchasePrice();
        int profitRate = (int) Math.round((stock.getPrice() - avgPurchasePrice) / avgPurchasePrice * 100);
        
        // PlayerStockResponse 객체 생성을 위해 미리 객체를 만들어 둠
        PlayerStockResponse response = playerStockMapper.toResponse(player, request.stockQuantity(), playerStock);
    
        if (playerStock.getQuantity() == request.stockQuantity()) {
            player.getPlayerStockList().remove(playerStock); // 보유 주식에서 제거 
        } else {
            playerStock.subtractQuantity(request.stockQuantity(), totalPrice); // 수량만 감소 
        }
        
        // 돈 증가
        player.addMoney(totalPrice);
    
        // 거래 이력 추가 (수익률 포함)
        PlayerStockHistory history = new PlayerStockHistory(
            UUID.randomUUID(),
            player,
            stock,
            "매도",
            request.stockQuantity(),
            stock.getPrice(),
            totalPrice,
            profitRate,  // 거래 시점의 수익률
            LocalDateTime.now()
        );
        playerStockHistoryRepository.save(history);
    
        return response;
    }

    // 플레이어 자금본 추가 
    @Transactional
    public PlayerResponse addPlayerMoney(UUID playerId, AddMoneyRequest request) {
        Player player = findById(playerId);
        player.addMoney(request.money());
        return playerMapper.toResponse(player);
    }

    // 플레이어 목록 확인
    public List<PlayerResponse> getPlayerList(Pageable pageable) {
        Page<Player> playerList = playerRepository.findAll(pageable);

        if (playerList.isEmpty()) {
            return null;
        }

        return playerList.getContent().stream()
                .map(playerMapper::toResponse).toList();
    }   
}