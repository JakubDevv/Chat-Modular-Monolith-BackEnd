package org.example.user_application.services;

import enums.FriendStatus;
import org.example.user_application.dto.friends.FriendOnlineDto;
import org.example.user_application.dto.friends.FriendShortDto;
import org.example.user_application.dto.friends.FriendsCountriesDto;
import org.example.user_application.dto.friends.FriendsOnlineDto;
import org.example.user_application.dto.interests.InterestStatsDto;
import org.example.user_application.dto.users.UserShortDto;

import java.util.List;

public interface IFriendService {

    List<InterestStatsDto> getFriendsInterests(Long userId);

    List<FriendOnlineDto> getActiveFriends(Long userId);

    FriendsOnlineDto getActiveFriendsCount(Long userId);

    List<UserShortDto> getUsersByMutualFriends(Long userId);

    List<FriendsCountriesDto> getFriendsCountries(Long userId);

    List<FriendShortDto> getFriends(Long userId);

    void createFriendRequest(Long userId, Long userId2);

    void acceptFriendRequest(Long userId, Long userId2);

    void removeFriend(Long userId, Long userId2);

    FriendStatus getFriendshipStatus(Long userId, Long userId2);

    Long countCommonFriends(Long userId, Long userId2);
}
