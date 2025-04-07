// SignUpRequest.java
package com.skala.spring_stockmarket.dto.request;

public record SignUpRequest(
    String nickname,
    String password,
    int money
) {}