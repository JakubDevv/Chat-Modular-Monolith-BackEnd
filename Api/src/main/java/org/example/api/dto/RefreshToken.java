package org.example.api.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshToken(@NotBlank String value) {

}