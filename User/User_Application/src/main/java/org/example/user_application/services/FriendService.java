package org.example.user_application.services;

import aggregates.UserAggregate;
import enums.FriendStatus;
import models.User;
import org.example.user_application.dto.friends.FriendOnlineDto;
import org.example.user_application.dto.friends.FriendShortDto;
import org.example.user_application.dto.friends.FriendsCountriesDto;
import org.example.user_application.dto.friends.FriendsOnlineDto;
import org.example.user_application.dto.interests.InterestStatsDto;
import org.example.user_application.dto.users.UserShortDto;
import org.example.user_application.repository.IFriendRepository;
import org.example.user_application.repository.IUserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendService implements IFriendService {

    private final ApplicationEventPublisher eventPublisher;
    private final IUserRepository userRepository;
    private final IFriendRepository friendRepository;

    public FriendService(ApplicationEventPublisher eventPublisher, IUserRepository userRepository, IFriendRepository friendRepository) {
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    @Override
    public List<InterestStatsDto> getFriendsInterests(Long userId) {
        return friendRepository.getFriendsInterestsByPercentage(userId);
    }

    @Override
    public List<FriendOnlineDto> getActiveFriends(Long userId) {
        return List.of();
    }

    @Override
    public FriendsOnlineDto getActiveFriendsCount(Long userId) {
        return new FriendsOnlineDto(friendRepository.countOnlineFriends(userId, LocalDateTime.now().minusMinutes(2L)), friendRepository.countFriends(userId));
    }

    @Override
    public List<UserShortDto> getUsersByMutualFriends(Long userId) {
        List<UserShortDto> list = new ArrayList<>(
                userRepository.getAllUsers().stream().filter(userr -> !Objects.equals(userr.getId().value(), userId))
                        .map(userr -> {
                            return new UserShortDto(userr.getId().value(), userr.getFullName().firstName(), userr.getFullName().lastName(), friendRepository.getFriendshipStatus(userId, userr.getId().value()), userr.getCreated(), friendRepository.countCommonFriends(userId, userr.getId().value()));
                        }).toList());
        list.sort((a,b) -> Long.compare(b.mutualFriends(), a.mutualFriends()));
        return list.stream().limit(6).toList();
    }

    @Override
    public List<FriendsCountriesDto> getFriendsCountries(Long userId) {
        return friendRepository.getFriendsCountries(userId);
    }

    @Override
    public List<FriendShortDto> getFriends(Long userId) {
        return friendRepository.getFriends(userId).stream().map(user -> {
            User userById = userRepository.getUserById(userId);
            return new FriendShortDto(
                    userById.getId().value(),
                    userById.getBgColor().value(),
                    userById.getFullName().firstName(),
                    userById.getFullName().lastName(),
                    userById.getLocalization().country(),
                    userById.getLocalization().city(),
                    friendRepository.countCommonFriends(userId, userById.getId().value()),
                    0L,
                    userById.getUserStatus()
            );
        }).toList();
    }

    @Override
    public void createFriendRequest(Long userId, Long userId2) {
        UserAggregate user = userRepository.getUserAggregateByUserId(userId);
        user.sendFriendRequest(userId2);
        user.getEvents().forEach(eventPublisher::publishEvent);
    }

    @Override
    public void acceptFriendRequest(Long userId, Long userId2) {
        UserAggregate user = userRepository.getUserAggregateByUserId(userId);
        user.acceptFriendRequest(userId2);
        user.getEvents().forEach(eventPublisher::publishEvent);
    }

    @Override
    public void removeFriend(Long userId, Long userId2) {
        UserAggregate user = userRepository.getUserAggregateByUserId(userId);
        user.removeFriend(userId2);
        user.getEvents().forEach(eventPublisher::publishEvent);
    }

    @Override
    public FriendStatus getFriendshipStatus(Long userId, Long userId2) {
        return friendRepository.getFriendshipStatus(userId, userId2);
    }

    @Override
    public Long countCommonFriends(Long userId, Long userId2) {
        return 0L;
    }
}
