package org.example.messages_application.events;

import enums.GroupNotificationType;

public class UserAddedToGroupEvent extends UserGroupEvent {

    private GroupNotificationType groupNotificationType;

    public UserAddedToGroupEvent(Long sender, Long receiver, String group_name) {
        super(sender, receiver, group_name);
        this.groupNotificationType = GroupNotificationType.GROUP_ADDED;
    }

    public GroupNotificationType getGroupNotificationType() {
        return groupNotificationType;
    }
}
