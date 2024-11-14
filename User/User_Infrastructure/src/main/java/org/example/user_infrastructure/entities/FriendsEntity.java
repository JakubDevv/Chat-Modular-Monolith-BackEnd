package org.example.user_infrastructure.entities;

import enums.FriendStatus;
import jakarta.persistence.*;
import org.example.user_infrastructure.entities.id.FriendID;

import java.time.LocalDateTime;

@Entity
@Table(name = "friends")
public class FriendsEntity {

    @EmbeddedId
    private FriendID friendID;

    @Enumerated(value = EnumType.STRING)
    private FriendStatus status;

    private LocalDateTime last_update;

    public FriendsEntity(FriendID friendID, FriendStatus status, LocalDateTime last_update) {
        this.friendID = friendID;
        this.status = status;
        this.last_update = last_update;
    }

    public FriendsEntity() {
    }

    public FriendID getFriendID() {
        return friendID;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }
}
