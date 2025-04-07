// PlayerResponse.java
package com.skala.spring_stockmarket.dto.response;

import java.util.UUID;

public record PlayerResponse(
    UUID id,
    String nickname,
    int money
) {}