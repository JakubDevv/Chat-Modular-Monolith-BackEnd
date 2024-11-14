package org.example.messages_infrastructure.entities;

import enums.GroupRole;
import jakarta.persistence.*;
import org.example.messages_infrastructure.entities.composedKey.GroupUserIDKey;

import java.time.LocalDateTime;

@Entity
@Table(name = "groups_users")
public class GroupUserEntity {

    @EmbeddedId
    private GroupUserIDKey groupUserID;

    private Long unread_messages;

    @Enumerated(value = EnumType.STRING)
    private GroupRole role;

    private LocalDateTime added;

    public GroupUserEntity(Long userId, GroupEntity chatId, Long unread_messages, GroupRole role, LocalDateTime added) {
        this.groupUserID = new GroupUserIDKey(userId, chatId);
        this.unread_messages = unread_messages;
        this.role = role;
        this.added = added;
    }

    public GroupUserEntity() {
    }

    public GroupUserIDKey getGroupUserID() {
        return groupUserID;
    }

    public Long getUnread_messages() {
        return unread_messages;
    }

    public GroupRole getRole() {
        return role;
    }

    public LocalDateTime getAdded() {
        return added;
    }
}
