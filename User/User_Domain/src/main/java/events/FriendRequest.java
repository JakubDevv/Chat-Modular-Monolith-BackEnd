package events;

import enums.FriendStatus;
import vo.UserId;

public class FriendRequest extends FriendshipStatusChangeEvent {

    public FriendRequest(UserId userId, UserId friendId) {
        super(userId, friendId, FriendStatus.PENDING);
    }
}