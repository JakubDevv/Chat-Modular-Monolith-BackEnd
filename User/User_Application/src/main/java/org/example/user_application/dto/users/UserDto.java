package org.example.user_application.dto.users;

public record UserDto(Long id, String firstName, String lastName, String email, String country, String city, Long unreadMessages) {
}