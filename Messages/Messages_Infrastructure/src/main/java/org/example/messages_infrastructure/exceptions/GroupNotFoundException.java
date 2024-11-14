package org.example.messages_infrastructure.exceptions;

import org.example.shared.exception.types.NotFoundException;

public class GroupNotFoundException extends NotFoundException {

    public GroupNotFoundException(Long id) {
        super(String.format(
                "Group with id = %d not found in database",
                id
        ));
    }

}
