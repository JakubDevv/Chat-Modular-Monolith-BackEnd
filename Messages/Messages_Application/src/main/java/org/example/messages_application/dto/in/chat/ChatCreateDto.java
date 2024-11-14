package org.example.messages_application.dto.in.chat;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ChatCreateDto(@NotBlank String name, @NotEmpty List<Long> ids) {
}
