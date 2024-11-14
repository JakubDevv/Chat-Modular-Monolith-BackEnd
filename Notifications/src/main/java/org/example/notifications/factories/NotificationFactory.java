package org.example.notifications.factories;

import events.FriendshipStatusChangeEvent;
import org.example.notifications.consts.NotificationType;
import org.example.notifications.entity.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationFactory implements INotificationFactory {

    @Override
    public Notification create(FriendshipStatusChangeEvent event) {
        NotificationType type = switch (event.getFriendStatus()) {
            case NONE -> NotificationType.FRIEND_REMOVED;
            case TO_ACCEPT -> NotificationType.FRIEND_REQUEST;
            case ACCEPTED -> NotificationType.FRIEND_ACCEPT;
            case PENDING -> null;
        };
        return new Notification(event.getUserId().value(), event.getFriendId().value(), type);
    }

}
