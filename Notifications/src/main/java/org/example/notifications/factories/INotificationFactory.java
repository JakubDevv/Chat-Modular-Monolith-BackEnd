package org.example.notifications.factories;

import events.FriendshipStatusChangeEvent;
import org.example.notifications.entity.Notification;

public interface INotificationFactory {

    Notification create(FriendshipStatusChangeEvent event);
}
