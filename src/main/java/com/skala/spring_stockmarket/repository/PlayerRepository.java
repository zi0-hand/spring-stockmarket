package com.skala.spring_stockmarket.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skala.spring_stockmarket.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, UUID>{
    
    Optional<Player> findByNickname(String nickname);

    // 목적: findbyid를 하면 플레이어만 가져오는데, 위의 쿼리를 써주면 playerstock 정보들도 모두 가져와주고 싶어서 이렇게 사용
    // 명명규칙이 없는 것은 아래처럼 직접 쿼리를 써준다 (조건 3개를 넘으면, 이렇게 쿼리를 많이 쓴다)
    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.playerStockList WHERE p.id = :playerId")
    Optional<Player> findByIdWithStocks(@Param("playerId") UUID playerId);

}
