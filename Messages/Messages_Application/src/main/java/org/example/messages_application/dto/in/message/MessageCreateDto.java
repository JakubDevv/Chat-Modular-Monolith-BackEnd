package org.example.messages_application.dto.in.message;

public record MessageCreateDto(Long sender_id, String content, Long response_id, boolean media) {
}
