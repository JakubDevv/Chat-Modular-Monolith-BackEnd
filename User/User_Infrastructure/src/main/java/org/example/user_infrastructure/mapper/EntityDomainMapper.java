package org.example.user_infrastructure.mapper;

import models.Friend;
import models.User;
import org.example.user_infrastructure.entities.FriendsEntity;
import org.example.user_infrastructure.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class EntityDomainMapper {

    public User userEntityToDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getFirst_name(),
                userEntity.getLast_name(),
                userEntity.getUsername(),
                userEntity.getCountry(),
                userEntity.getCity(),
                userEntity.getCreated(),
                userEntity.getBgcolor(),
                userEntity.getBirth(),
                userEntity.getLast_activity(),
                userEntity.getPassword());
    }

    public Friend friendEntityToDomain(FriendsEntity friendsEntity) {
        return new Friend(
                friendsEntity.getFriendID().getUser_id().getId(),
                friendsEntity.getFriendID().getFriend_id().getId(),
                friendsEntity.getStatus(),
                friendsEntity.getLast_update()
        );
    }

    public UserEntity userDomainToEntity(User user) {
        return new UserEntity(
                user.getId().value(),
                user.getEmail().value(),
                user.getUsername(),
                user.getFullName().firstName(),
                user.getFullName().lastName(),
                user.getPassword().value(),
                user.getLocalization().country(),
                user.getLocalization().city(),
                user.getCreated(),
                user.getBgColor().value(),
                user.getBirth(),
                user.getLast_activity());
    }

}
