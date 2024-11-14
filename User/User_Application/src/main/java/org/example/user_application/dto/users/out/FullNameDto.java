package org.example.user_application.dto.users.out;

public record FullNameDto(String firstName, String lastName) {

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
