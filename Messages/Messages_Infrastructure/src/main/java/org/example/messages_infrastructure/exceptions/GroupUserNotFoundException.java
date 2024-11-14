package org.example.messages_infrastructure.exceptions;

import models.id.GroupUserID;
import org.example.messages_infrastructure.entities.composedKey.GroupUserIDKey;
import org.example.shared.exception.types.NotFoundException;

public class GroupUserNotFoundException extends NotFoundException {

    public GroupUserNotFoundException(Long id) {
        super(String.format(
                "GroupUser with id = %d not found in database",
                id
        ));
    }

    public GroupUserNotFoundException(GroupUserIDKey id) {
        super(String.format(
                "GroupUser with user id = %d and group id = %d not found in database",
                id.getUser_id(), id.getGroup_id().getId()
        ));
    }
}
