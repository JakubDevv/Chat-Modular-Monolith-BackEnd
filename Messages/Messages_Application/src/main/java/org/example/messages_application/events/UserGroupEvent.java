package org.example.messages_application.events;

public class UserGroupEvent {

    private final Long sender;
    private final Long receiver;
    private final String group_name;

    public UserGroupEvent(Long sender, Long receiver, String group_name) {
        this.sender = sender;
        this.receiver = receiver;
        this.group_name = group_name;
    }

    public Long getSender() {
        return sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public String getGroup_name() {
        return group_name;
    }
}
