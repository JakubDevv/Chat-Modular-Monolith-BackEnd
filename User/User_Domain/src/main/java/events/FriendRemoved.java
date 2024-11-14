package events;

import enums.FriendStatus;
import vo.UserId;

public class FriendRemoved extends FriendshipStatusChangeEvent {

    public FriendRemoved(UserId userId, UserId friendId) {
        super(userId, friendId, FriendStatus.NONE);
    }

}
