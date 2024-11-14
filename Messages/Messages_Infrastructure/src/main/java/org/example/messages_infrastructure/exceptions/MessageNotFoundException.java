package org.example.messages_infrastructure.exceptions;

import org.example.shared.exception.types.NotFoundException;

public class MessageNotFoundException extends NotFoundException {

    public MessageNotFoundException(Long id) {
        super(String.format(
                "Message with id = %d not found in database",
                id
        ));
    }

}
