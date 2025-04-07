// LoginRequest.java
package com.skala.spring_stockmarket.dto.request;

public record LoginRequest(
    String nickname,
    String password
) {}