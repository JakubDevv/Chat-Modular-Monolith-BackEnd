package events;

import enums.FriendStatus;
import vo.UserId;

public class FriendAccepted extends FriendshipStatusChangeEvent {

    public FriendAccepted(UserId userId, UserId friendId) {
        super(userId, friendId, FriendStatus.ACCEPTED);
    }
}