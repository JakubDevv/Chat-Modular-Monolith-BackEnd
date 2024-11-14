package org.example.api.controller;

import jakarta.validation.constraints.Min;
import org.example.api.aop.UpdateActivity;
import org.example.user_application.services.IFriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@UpdateActivity
@RestController
@RequestMapping("/friend")
public class FriendController {

    private final IFriendService friendService;

    public FriendController(IFriendService friendService) {
        this.friendService = friendService;
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFriend(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @RequestParam Long userId){
        friendService.removeFriend(authenticatedUserId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Void> createFriendRequest(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @RequestParam Long userId){
        friendService.createFriendRequest(authenticatedUserId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> acceptFriendRequest(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @RequestParam Long userId){
        friendService.acceptFriendRequest(authenticatedUserId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
