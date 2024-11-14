package org.example.user_application.services;

import org.example.user_application.dto.friends.FriendDto;
import org.example.user_application.dto.interests.InterestCreateDto;
import org.example.user_application.dto.interests.InterestDto;
import org.example.user_application.dto.users.UserImageDto;
import org.example.user_application.dto.users.UserDto;
import org.example.user_application.dto.users.UserShortDto;
import org.example.user_application.dto.users.out.FullNameDto;
import models.User;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public interface IUserService {

    List<InterestDto> getInterestsByUserId(Long userId);

    Long addInterest(Long userId, InterestCreateDto interest);

    void removeInterestById(Long userId, Long value);

    UserDto getPersonalData(Long userId);

    List<FriendDto> getUsers(Long userId);

    void changeBackgroundColor(Long userId, String newColor);

    List<UserShortDto> getNewUsers(Long userId);

    UserImageDto getUserBackgroundColor(Long userId);

    byte[] getUserProfilePicture(Long userId);

    void updateUserProfilePicture(Long userId, InputStream file);

    User getUserByUsername(String username);
    User getUserById(Long id);

    FullNameDto getUsersFullNameById(Long userId);

    void updateUsersLastActivity(Long userId);

    User findUserByUsername(String username);

    void saveUser(User user);

    Long countOnlineUsers(List<Long> usersId);
}
