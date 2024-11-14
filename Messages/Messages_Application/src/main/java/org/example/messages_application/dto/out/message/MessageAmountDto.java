package org.example.messages_application.dto.out.message;

import java.time.LocalDateTime;

public record MessageAmountDto(LocalDateTime date, Long amount) {
}
