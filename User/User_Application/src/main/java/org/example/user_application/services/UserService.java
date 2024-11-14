package org.example.user_application.services;

import enums.UserStatus;
import org.example.user_application.dto.friends.FriendDto;
import org.example.user_application.dto.interests.InterestCreateDto;
import org.example.user_application.dto.interests.InterestDto;
import org.example.user_application.dto.users.UserImageDto;
import org.example.user_application.dto.users.UserDto;
import org.example.user_application.dto.users.UserShortDto;
import org.example.user_application.dto.users.out.FullNameDto;
import models.Interest;
import models.User;
import org.example.shared.s3.S3Service;
import org.springframework.stereotype.Service;
import org.example.user_application.repository.IFriendRepository;
import org.example.user_application.repository.IUserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IFriendRepository friendRepository;
    private final S3Service s3Service;

    public UserService(IUserRepository userRepository, IFriendRepository friendRepository, S3Service s3Service) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
        this.s3Service = s3Service;
    }

    @Override
    public List<InterestDto> getInterestsByUserId(Long userId) {
        return userRepository.getUserInterestsByUserId(userId)
                .stream().map(interest -> new InterestDto(interest.getId(), interest.getValue())).toList();
    }

    @Override
    public Long addInterest(Long userId, InterestCreateDto interest) {
        return userRepository.addInterest(userId, interest.value());
    }

    @Override
    public void removeInterestById(Long userId, Long interestId) {
        userRepository.removeInterestById(interestId);
    }

    @Override
    public UserDto getPersonalData(Long userId) {
        User user = userRepository.getUserById(userId);
        return new UserDto(user.getId().value(), user.getFullName().firstName(), user.getFullName().lastName(), user.getEmail().value(), user.getLocalization().country(), user.getLocalization().city(), 0L);
    }

    @Override
    public List<FriendDto> getUsers(Long userId) {
        return userRepository.getAllUsers().stream().filter(user -> !user.getId().value().equals(userId)).map(user -> {
                return new FriendDto(
                        user.getId().value(),
                        user.getBgColor().value(),
                        user.getFullName().firstName(),
                        user.getFullName().lastName(),
                        user.getLocalization().country(),
                        user.getLocalization().city(),
                        friendRepository.getFriendshipStatus(userId, user.getId().value()),
                        user.getAge(),
                        userRepository.getUserInterestsByUserId(user.getId().value()).stream().map(Interest::getValue).toList(),
                        friendRepository.countFriends(user.getId().value()),
                        friendRepository.countCommonFriends(userId, user.getId().value()),
                        userRepository.countAllGroup(userId),
                        userRepository.countAllCommonGroups(userId, user.getId().value()),
                        user.getBirth(),
                        user.getUserStatus()
                        );
            }
        ).toList();
    }

    @Override
    public void changeBackgroundColor(Long userId, String newColor) {
        User userById = userRepository.getUserById(userId);
        userById.setBgColor(newColor);
        userRepository.save(userById);
    }

    @Override
    public List<UserShortDto> getNewUsers(Long userId) {
        User user1 = userRepository.getUserById(userId);
        return userRepository.getAllUsers().stream().sorted(Comparator.comparing(User::getCreated))
                .limit(5)
                .map(user -> new UserShortDto(
                        userId,
                        user.getFullName().firstName(),
                        user.getFullName().lastName(),
                        friendRepository.getFriendshipStatus(user1.getId().value(), user.getId().value()),
                        user.getCreated(),
                        friendRepository.countCommonFriends(user1.getId().value(), user.getId().value()))).toList();

    }

    @Override
    public UserImageDto getUserBackgroundColor(Long userId) {
        User user = userRepository.getUserById(userId);
        return new UserImageDto(user.getId().value(), user.getBgColor().value());
    }

    @Override
    public byte[] getUserProfilePicture(Long userId) {
        try {
            return s3Service.getObject("Chat/User" + userId);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void updateUserProfilePicture(Long userId, InputStream file) {
        try {
            s3Service.putObject("Chat/User" + userId, file.readAllBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public FullNameDto getUsersFullNameById(Long userId) {
        User user = userRepository.getUserById(userId);
        return new FullNameDto(user.getFullName().firstName(), user.getFullName().lastName());
    }

    @Override
    public void updateUsersLastActivity(Long userId) {
        User user = userRepository.getUserById(userId);
        user.updateLastActivity();
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public Long countOnlineUsers(List<Long> usersId) {
        return usersId.stream().filter(user -> userRepository.getUserById(user).getUserStatus() == UserStatus.ONLINE).count();
    }

}
