package events;

import enums.FriendStatus;
import vo.UserId;

public class FriendshipStatusChangeEvent {

    private final UserId userId;
    private final UserId friendId;
    private final FriendStatus friendStatus;

    public FriendshipStatusChangeEvent(UserId userId, UserId friendId, FriendStatus friendStatus) {
        this.userId = userId;
        this.friendId = friendId;
        this.friendStatus = friendStatus;
    }

    public UserId getUserId() {
        return userId;
    }

    public UserId getFriendId() {
        return friendId;
    }

    public FriendStatus getFriendStatus() {
        return friendStatus;
    }
}
