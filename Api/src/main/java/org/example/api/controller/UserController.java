package org.example.api.controller;

import org.example.api.aop.UpdateActivity;
import org.example.messages_application.repository.IMessageRepository;
import org.example.messages_application.services.IMessageService;
import org.example.notifications.services.INotificationService;
import org.example.user_application.dto.friends.*;
import org.example.user_application.dto.interests.InterestCreateDto;
import org.example.user_application.dto.interests.InterestDto;
import org.example.user_application.dto.interests.InterestStatsDto;
import org.example.user_application.dto.users.UserDto;
import org.example.user_application.dto.users.UserImageDto;
import org.example.user_application.dto.users.UserShortDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.messages_application.dto.out.message.MessageAmountDto;
import org.example.notifications.dto.NotificationDto;
import org.example.user_application.services.IFriendService;
import org.example.user_application.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@UpdateActivity
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final IFriendService friendService;
    private final INotificationService notificationService;
    private final IMessageService messageService;

    public UserController(IUserService userService, IFriendService friendService, INotificationService notificationService, IMessageService messageService) {
        this.userService = userService;
        this.friendService = friendService;
        this.notificationService = notificationService;
        this.messageService = messageService;
    }

    @GetMapping("/personal-data")
    public ResponseEntity<UserDto> getPersonalData(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(userService.getPersonalData(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/image")
    public ResponseEntity<UserImageDto> getUserBackgroundColor(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(userService.getUserBackgroundColor(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/interests")
    public ResponseEntity<List<InterestDto>> getInterestsByUser(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(userService.getInterestsByUserId(authenticatedUserId), HttpStatus.OK);
    }

    @DeleteMapping("/interests")
    public ResponseEntity<Void> deleteInterestById(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @RequestParam Long id) {
        userService.removeInterestById(authenticatedUserId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/interests")
    public ResponseEntity<Long> addInterest(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Valid @RequestBody InterestCreateDto interestCreateDTO) {
        return new ResponseEntity<>(userService.addInterest(authenticatedUserId, interestCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/friends/interests")
    public ResponseEntity<List<InterestStatsDto>> getFriendsInterests(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(friendService.getFriendsInterests(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<FriendDto>> getUsers(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(userService.getUsers(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/messages/amount")
    public ResponseEntity<List<MessageAmountDto>> getSentMessagesByTime(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(messageService.getSentMessagesByTime(authenticatedUserId), HttpStatus.OK);
    }

    @PutMapping("/bg-color")
    public ResponseEntity<Void> changeBackgroundColor(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @NotBlank @RequestParam String bgColor) {
        userService.changeBackgroundColor(authenticatedUserId, bgColor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active-friends")
    public ResponseEntity<List<FriendOnlineDto>> getActiveFriends(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(friendService.getActiveFriends(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/active-friends-count")
    public ResponseEntity<FriendsOnlineDto> getActiveFriendsCount(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(friendService.getActiveFriendsCount(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<List<UserShortDto>> getNewUsers(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(userService.getNewUsers(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/mutual-friends")
    public ResponseEntity<List<UserShortDto>> getUsersByMutualFriends(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(friendService.getUsersByMutualFriends(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<FriendsCountriesDto>> getFriendsCountries(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(friendService.getFriendsCountries(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(notificationService.getNotificationsByUserId(authenticatedUserId), HttpStatus.OK);
    }

    @DeleteMapping("/notification")
    public ResponseEntity<Void> deleteNotificationById(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @RequestParam Long id) {
        notificationService.deleteNotificationById(authenticatedUserId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile-picture/{id}")
    public ResponseEntity<?> getUserProfilePictureById(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserProfilePicture(id), HttpStatus.OK);
    }

    @PostMapping("/profile-picture")
    public ResponseEntity<Void> updateUserProfilePicture(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @RequestParam(value = "file") MultipartFile file) {
        try (InputStream byteFile = file.getInputStream()){
            userService.updateUserProfilePicture(authenticatedUserId, byteFile);
        } catch (IOException e) {

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/friends")
    public ResponseEntity<List<FriendShortDto>> getFriends(@RequestHeader("X-auth-user-id") Long authenticatedUserId) {
        return new ResponseEntity<>(friendService.getFriends(authenticatedUserId), HttpStatus.OK);
    }
}
