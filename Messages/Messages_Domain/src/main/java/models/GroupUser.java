package models;

import enums.GroupRole;
import models.id.GroupUserID;

import java.time.LocalDateTime;

public class GroupUser {

    private GroupUserID groupUserID;

    private Long unread_messages;

    private GroupRole role;

    private LocalDateTime added;

    public GroupUser(GroupUserID groupUserID, Long unread_messages) {
        this.groupUserID = groupUserID;
        this.unread_messages = unread_messages;
        this.role = GroupRole.USER;
        this.added = LocalDateTime.now();
    }

    public GroupUser(GroupUserID groupUserID, GroupRole role, Long unread_messages) {
        this.groupUserID = groupUserID;
        this.unread_messages = unread_messages;
        this.role = role;
        this.added = LocalDateTime.now();
    }

    public GroupUser(GroupUserID groupUserID, Long unread_messages, GroupRole role, LocalDateTime added) {
        this.groupUserID = groupUserID;
        this.unread_messages = unread_messages;
        this.role = role;
        this.added = added;
    }

    public void readAllMessages(){
        this.unread_messages = 0L;
    }

    public void appendUserUnreadMessagesByOne(){
        this.unread_messages += 1;
    }

    public Long getGroupID() {
        return groupUserID.getGroup_id().value();
    }

    public Long getUserID() {
        return groupUserID.getUser_id().value();
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
