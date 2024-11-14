package org.example.api.dto;

public record UserCreatedDto(Long id, String refreshToken, String accessToken) {
}
