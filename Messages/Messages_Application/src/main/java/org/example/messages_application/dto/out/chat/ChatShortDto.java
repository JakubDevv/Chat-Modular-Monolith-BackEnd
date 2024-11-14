package org.example.messages_application.dto.out.chat;

import enums.GroupNotificationType;

import java.time.LocalDateTime;

public record ChatShortDto(Long id,
                           LocalDateTime lastMessageTime,
                           String lastMessage,
                           Long unread_messages,
                           Long lastMessageUserId,
                           String lastMessageUser,
                           String chatName,
                           String bgColor,
                           Long userId,
                           boolean isChat,
                           Long activeUsers,
                           GroupNotificationType notificationType) {
}
