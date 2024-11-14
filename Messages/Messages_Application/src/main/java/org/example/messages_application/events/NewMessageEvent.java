package org.example.messages_application.events;

import org.example.messages_application.dto.in.message.MessageDto;
import org.springframework.context.ApplicationEvent;

public class NewMessageEvent extends ApplicationEvent {

    private MessageDto messageDto;
    private Long userId;

    public NewMessageEvent(Object source, MessageDto messageDto, Long userId) {
        super(source);
        this.messageDto = messageDto;
        this.userId = userId;
    }

    public MessageDto getMessageDto() {
        return messageDto;
    }

    public int getUserId() {
        return userId.intValue();
    }
}
