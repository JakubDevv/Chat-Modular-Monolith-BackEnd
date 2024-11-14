package org.example.messages_application.dto;

import enums.FriendStatus;
import enums.UserStatus;

public record ChatUserDto(Long id, String firstName, String lastName, FriendStatus friendStatus, Long messages, Long mutualFriends, UserStatus userStatus, String bgColor) {
}
