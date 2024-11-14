package org.example.user_infrastructure.services;

import enums.FriendStatus;
import models.Friend;
import org.example.user_application.dto.friends.FriendsCountriesDto;
import org.example.user_application.dto.interests.InterestStatsDto;
import org.example.user_infrastructure.mapper.EntityDomainMapper;
import org.example.user_infrastructure.repositories.FriendsRepositoryDB;
import org.example.user_infrastructure.repositories.UserRepositoryDB;
import org.springframework.stereotype.Service;
import org.example.user_application.repository.IFriendRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendRepositoryImpl implements IFriendRepository {

    private final EntityDomainMapper entityDomainMapper;
    private final FriendsRepositoryDB friendRepository;

    public FriendRepositoryImpl(EntityDomainMapper entityDomainMapper, FriendsRepositoryDB friendRepository) {
        this.entityDomainMapper = entityDomainMapper;
        this.friendRepository = friendRepository;
    }

    @Override
    public Long countCommonFriends(Long user1, Long user2) {
        return friendRepository.countCommonFriends(user1, user2);
    }

    @Override
    public FriendStatus getFriendshipStatus(Long user1, Long user2) {
        return friendRepository.getUsersStatus(user1, user2);
    }

    @Override
    public Long countFriends(Long user1) {
        return friendRepository.getAllFriends(user1);
    }

    @Override
    public Long countOnlineFriends(Long user1, LocalDateTime activeThreshold) {
        return friendRepository.getOnlineFriendsCount(user1, activeThreshold);
    }

    @Override
    public List<Friend> getFriends(Long userId) {
        return friendRepository.getFriends2(userId).stream().map(entityDomainMapper::friendEntityToDomain).toList();
    }

    @Override
    public List<InterestStatsDto> getFriendsInterestsByPercentage(Long userId) {
        return friendRepository.getFriendsInterestsByPercentage(userId);
    }

    @Override
    public List<FriendsCountriesDto> getFriendsCountries(Long userId) {
        return friendRepository.getFriendsCountries(userId);
    }
}
