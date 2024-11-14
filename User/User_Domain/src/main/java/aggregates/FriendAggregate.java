package aggregates;

import enums.FriendStatus;
import vo.UserId;

public class FriendAggregate {

    private UserId userId;

    private FriendStatus status;

    public FriendAggregate(Long userId, FriendStatus status) {
        this.userId = new UserId(userId);
        this.status = status;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    public UserId getUserId() {
        return userId;
    }

    public FriendStatus getStatus() {
        return status;
    }
}
