package org.example.user_infrastructure.entities.id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.user_infrastructure.entities.UserEntity;

import java.io.Serializable;

@Embeddable
public class FriendID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user_id;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private UserEntity friend_id;

    public FriendID(){}

    public FriendID(UserEntity user_id, UserEntity friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public UserEntity getUser_id() {
        return user_id;
    }

    public UserEntity getFriend_id() {
        return friend_id;
    }

}
