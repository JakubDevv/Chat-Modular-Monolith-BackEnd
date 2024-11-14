package org.example.messages_application.events;

import enums.GroupNotificationType;

public class UserRemovedFromGroupEvent extends UserGroupEvent {

    private GroupNotificationType groupNotificationType;

    public UserRemovedFromGroupEvent(Long sender, Long receiver, String group_name) {
        super(sender, receiver, group_name);
        this.groupNotificationType = GroupNotificationType.GROUP_KICKED;
    }

    public GroupNotificationType getGroupNotificationType() {
        return groupNotificationType;
    }
}