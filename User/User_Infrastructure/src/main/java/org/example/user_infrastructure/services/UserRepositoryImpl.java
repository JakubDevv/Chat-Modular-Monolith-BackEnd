package org.example.user_infrastructure.services;

import aggregates.FriendAggregate;
import aggregates.UserAggregate;
import models.Interest;
import models.User;
import org.example.user_infrastructure.entities.InterestEntity;
import org.example.user_infrastructure.entities.UserEntity;
import org.example.user_infrastructure.exceptions.UserNotFoundException;
import org.example.user_infrastructure.mapper.EntityDomainMapper;
import org.example.user_infrastructure.repositories.FriendsRepositoryDB;
import org.example.user_infrastructure.repositories.InterestsRepositoryDB;
import org.example.user_infrastructure.repositories.UserRepositoryDB;
import org.springframework.stereotype.Service;
import org.example.user_application.repository.IUserRepository;

import java.util.List;

@Service
public class UserRepositoryImpl implements IUserRepository {

    private final UserRepositoryDB userRepositoryDB;
    private final EntityDomainMapper entityDomainMapper;
    private final FriendsRepositoryDB friendRepository;
    private final InterestsRepositoryDB interestsRepositoryDB;

    public UserRepositoryImpl(UserRepositoryDB userRepositoryDB, EntityDomainMapper entityDomainMapper, FriendsRepositoryDB friendRepository, InterestsRepositoryDB interestsRepositoryDB) {
        this.userRepositoryDB = userRepositoryDB;
        this.entityDomainMapper = entityDomainMapper;
        this.friendRepository = friendRepository;
        this.interestsRepositoryDB = interestsRepositoryDB;
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepositoryDB.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return entityDomainMapper.userEntityToDomain(userEntity);
    }

    @Override
    public User getUserByUsername(String username) {
        return entityDomainMapper.userEntityToDomain(userRepositoryDB.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));
    }

    @Override
    public UserAggregate getUserAggregateByUserId(Long id) {
        return new UserAggregate(id, userRepositoryDB.findAll().stream()
                .filter(user -> !user.getId().equals(id))
                .map(user -> new FriendAggregate(user.getId(), friendRepository.getUsersStatus(id, user.getId()))).toList()
                .stream().filter(friendAggregate -> friendAggregate.getStatus() != null).toList());
    }

    @Override
    public void save(UserAggregate user) {
    }

    @Override
    public List<Interest> getUserInterestsByUserId(Long id) {
        return userRepositoryDB.getInterestsByUserId(id).stream().map(interest -> new Interest(interest.getId(), interest.getValue())).toList();
    }

    @Override
    public void removeInterestById(Long id) {
        userRepositoryDB.deleteInterestById(id);
    }

    @Override
    public Long addInterest(Long userId, String interest) {
        UserEntity userEntity = userRepositoryDB.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return interestsRepositoryDB.save(new InterestEntity(interest, userEntity)).getId();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryDB.findAll().stream().map(entityDomainMapper::userEntityToDomain).toList();
    }

    @Override
    public void save(User user) {
        userRepositoryDB.save(entityDomainMapper.userDomainToEntity(user));
    }

    @Override
    public Long countAllGroup(Long userId) {
        return 0L;
    }

    @Override
    public Long countAllCommonGroups(Long userId, Long userId2) {
        return 0L;
    }
}
