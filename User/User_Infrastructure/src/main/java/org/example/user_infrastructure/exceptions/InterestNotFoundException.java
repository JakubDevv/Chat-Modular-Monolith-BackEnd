package org.example.user_infrastructure.exceptions;

import org.example.shared.exception.types.NotFoundException;

public class InterestNotFoundException extends NotFoundException {

    public InterestNotFoundException(Long id) {
        super(String.format(
                "Interest with id = %d not found in database",
                id
        ));
    }

}
