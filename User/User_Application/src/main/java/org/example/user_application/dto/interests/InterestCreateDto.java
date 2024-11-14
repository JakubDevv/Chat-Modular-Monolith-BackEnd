package org.example.user_application.dto.interests;

import jakarta.validation.constraints.NotBlank;

public record InterestCreateDto(@NotBlank String value) {
}
