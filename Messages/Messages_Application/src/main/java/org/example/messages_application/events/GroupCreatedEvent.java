package org.example.messages_application.events;

import enums.GroupNotificationType;

public class GroupCreatedEvent extends UserGroupEvent{

    private final GroupNotificationType groupNotificationType;

    public GroupCreatedEvent(Long sender, Long receiver, String group_name) {
        super(sender, receiver, group_name);
        this.groupNotificationType = GroupNotificationType.GROUP_CREATED;
    }

}
