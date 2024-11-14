package org.example.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthRequest(@NotBlank String username,
                              @NotBlank String password) {

}