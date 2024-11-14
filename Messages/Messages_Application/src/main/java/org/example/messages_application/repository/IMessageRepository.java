package org.example.messages_application.repository;

import models.Message;
import org.example.messages_application.dto.out.message.MessageAmountDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IMessageRepository {

    Message saveMessage(Message message);
    Message findById(Long id);

    List<MessageAmountDto> getSentMessagesByTime(Long userId);

    Long countMessagesInChat(Long chatId);
    Long countMessagesInChatByUserId(Long chatId, Long userId);

    List<Message> findAllByGroupIdOrderBySentAt(Long groupId);

    Message findLastMessageByGroupId(Long groupId);

}
