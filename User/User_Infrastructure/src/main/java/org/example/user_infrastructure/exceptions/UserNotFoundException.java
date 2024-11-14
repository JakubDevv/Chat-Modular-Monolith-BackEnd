package org.example.user_infrastructure.exceptions;

import org.example.shared.exception.types.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long id) {
        super(String.format(
                "User with id = %d not found in database",
                id
        ));
    }

    public UserNotFoundException(String id) {
        super(String.format(
                "User with username = %s not found in database",
                id
        ));
    }
}
