package org.example.user_application.repository;

import enums.FriendStatus;
import models.Friend;
import org.example.user_application.dto.friends.FriendsCountriesDto;
import org.example.user_application.dto.interests.InterestStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IFriendRepository {

    Long countCommonFriends(Long user1, Long user2);

    FriendStatus getFriendshipStatus(Long user1, Long user2);

    Long countFriends(Long user1);

    Long countOnlineFriends(Long user1, LocalDateTime activeThreshold);

    List<Friend> getFriends(Long userId);

    List<InterestStatsDto> getFriendsInterestsByPercentage(Long userId);

    List<FriendsCountriesDto> getFriendsCountries(Long userId);
}
