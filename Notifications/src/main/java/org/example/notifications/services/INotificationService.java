package org.example.notifications.services;

import org.example.notifications.dto.NotificationDto;

import java.util.List;

public interface INotificationService {

    List<NotificationDto> getNotificationsByUserId(Long userId);

    void deleteNotificationById(Long userId, Long notificationId);
}
