package org.example.notifications.dto;

import org.example.notifications.consts.NotificationType;

import java.time.LocalDateTime;

public record NotificationDto(Long id,
                             Long user_id,
                             String user_firstName,
                             String user_lastName,
                             LocalDateTime sentTime,
                             String group_name,
                             Long group_id,
                             NotificationType notificationType) {
}
