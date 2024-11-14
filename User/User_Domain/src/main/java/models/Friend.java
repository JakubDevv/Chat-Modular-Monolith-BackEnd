package models;

import enums.FriendStatus;
import vo.UserId;

import java.time.LocalDateTime;

public class Friend {

    private UserId userId;

    private UserId friendId;

    private FriendStatus status;

    private LocalDateTime last_update;

    public Friend(Long userId, Long friendId, FriendStatus status, LocalDateTime last_update) {
        this.userId = new UserId(userId);
        this.friendId = new UserId(friendId);
        this.status = status;
        this.last_update = last_update;
    }

    public UserId getUserId() {
        return userId;
    }

    public UserId getFriendId() {
        return friendId;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }
}
