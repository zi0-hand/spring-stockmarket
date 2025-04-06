package com.skala.spring_stockmarket.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerResponse {
    private UUID id;
    private String nickname;
    private int money;
}
