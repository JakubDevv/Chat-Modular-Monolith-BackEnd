package org.example.messages_infrastructure.entities.composedKey;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.messages_infrastructure.entities.GroupEntity;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class GroupUserIDKey implements Serializable {

    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group_id;

    public GroupUserIDKey(Long user_id, GroupEntity group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public GroupEntity getGroup_id() {
        return group_id;
    }

}
