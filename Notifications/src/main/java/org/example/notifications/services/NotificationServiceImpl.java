package org.example.notifications.services;

import org.example.notifications.exceptions.NotificationNotFound;
import org.example.user_application.dto.users.out.FullNameDto;
import org.example.notifications.dto.NotificationDto;
import org.example.notifications.entity.Notification;
import org.example.notifications.repository.NotificationRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.example.user_application.services.IUserService;

import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final IUserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, IUserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        return notificationRepository.getNotificationsByUserId(userId).stream().map(notification -> {
            FullNameDto user = userService.getUsersFullNameById(userId);
            return new NotificationDto(
                    notification.id(),
                    notification.user_id(),
                    user.firstName(),
                    user.lastName(),
                    notification.sentTime(),
                    notification.group_name(),
                    notification.group_id(),
                    notification.notificationType()
            );
        }).toList();
    }

    @Override
    public void deleteNotificationById(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFound(notificationId));
        if(notification.getReceiver().equals(userId)){
            notificationRepository.deleteById(notificationId);
        } else {
            throw new UnsupportedOperationException();
        }
    }

}
