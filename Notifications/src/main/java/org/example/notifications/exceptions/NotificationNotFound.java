package org.example.notifications.exceptions;

import org.example.shared.exception.types.NotFoundException;

public class NotificationNotFound extends NotFoundException {

    public NotificationNotFound(Long id) {
        super(String.format(
                "Notification with id = %d not found in database",
                id
        ));
    }

}
