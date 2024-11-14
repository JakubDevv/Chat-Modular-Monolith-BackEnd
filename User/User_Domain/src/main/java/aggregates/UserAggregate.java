package aggregates;

import enums.FriendStatus;
import events.FriendshipStatusChangeEvent;
import exceptions.friend.FriendNotFound;
import vo.UserId;

import java.util.ArrayList;
import java.util.List;

public class UserAggregate {

    private UserId id;

    private List<FriendAggregate> friends;

    private List<FriendshipStatusChangeEvent> events = new ArrayList<>();

    public UserAggregate(Long id, List<FriendAggregate> friends) {
        this.id = new UserId(id);
        this.friends = friends;
    }

    public void removeFriend(Long userId) {
        UserId userIdCopy = new UserId(userId);
        FriendAggregate friend = friends.stream().filter(user -> {
            return user.getUserId().equals(userIdCopy) && user.getStatus() == FriendStatus.ACCEPTED;
        }).findFirst().orElseThrow(() -> new FriendNotFound(userIdCopy.value()));
        friends.remove(friend);
//        events.add(new FriendRemoved(this, userIdCopy, id));
    }

    public void sendFriendRequest(Long userId) {
        UserId userIdCopy = new UserId(userId);
        boolean friend = friends.stream().anyMatch(user -> user.getUserId().equals(userIdCopy));
        if(friend) {
            throw new FriendNotFound(userIdCopy.value());
        } else {
            friends.add(new FriendAggregate(userIdCopy.value(), FriendStatus.PENDING));
//            events.add(new FriendRequest(this, userIdCopy, id));
        }
    }

    public void acceptFriendRequest(Long userId) {
        UserId userIdCopy = new UserId(userId);
        FriendAggregate friend = friends.stream().filter(user -> {
            return user.getUserId().equals(userIdCopy) && user.getStatus() == FriendStatus.TO_ACCEPT;
        }).findFirst().orElseThrow(() -> new FriendNotFound(userIdCopy.value()));
        friend.setStatus(FriendStatus.ACCEPTED);
//        events.add(new FriendAccepted(this, userIdCopy, id));
    }

    public UserId getId() {
        return id;
    }

    public List<FriendAggregate> getFriends() {
        return friends;
    }

    public List<FriendshipStatusChangeEvent> getEvents() {
        return events;
    }
}
