package com.skala.spring_stockmarket.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String nickname;
    private String password;
}
