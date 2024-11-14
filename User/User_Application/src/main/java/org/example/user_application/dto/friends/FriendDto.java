package org.example.user_application.dto.friends;

import enums.FriendStatus;
import enums.UserStatus;

import java.time.LocalDateTime;
import java.util.List;

public record FriendDto(Long user_id,
                        String bgColor,
                        String first_name,
                        String last_name,
                        String country,
                        String city,
                        FriendStatus status,
                        int age,
                        List<String> interests,
                        Long friends,
                        Long mutualFriends,
                        Long groups,
                        Long mutualGroups,
                        LocalDateTime joined,
                        UserStatus activityStatus) {
}
