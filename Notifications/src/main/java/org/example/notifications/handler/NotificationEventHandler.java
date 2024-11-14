package org.example.notifications.handler;

import events.FriendshipStatusChangeEvent;
import org.example.notifications.factories.NotificationFactory;
import org.example.notifications.repository.NotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventHandler {

    private final NotificationRepository notificationRepository;
    private final NotificationFactory notificationFactory;

    public NotificationEventHandler(NotificationRepository notificationRepository, NotificationFactory notificationFactory) {
        this.notificationRepository = notificationRepository;
        this.notificationFactory = notificationFactory;
    }

    @EventListener
    public void handle(FriendshipStatusChangeEvent event) {
        notificationRepository.save(notificationFactory.create(event));
    }
}
