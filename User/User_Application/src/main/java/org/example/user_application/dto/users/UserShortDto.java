package org.example.user_application.dto.users;

import enums.FriendStatus;

import java.time.LocalDateTime;

public record UserShortDto(Long id, String firstName, String lastName, FriendStatus friendStatus, LocalDateTime created, Long mutualFriends) {
}
