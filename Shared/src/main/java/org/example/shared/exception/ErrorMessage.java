package org.example.shared.exception;

import java.time.LocalDateTime;

public record ErrorMessage(String error, String message, LocalDateTime timestamp) {
}
