package org.example.user_application.dto.friends;

import enums.UserStatus;

public record FriendShortDto(Long id,
                             String bgColor,
                             String first_name,
                             String last_name,
                             String country,
                             String city,
                             Long mutualFriends,
                             Long mutualGroups,
                             UserStatus activityStatus) {
}
