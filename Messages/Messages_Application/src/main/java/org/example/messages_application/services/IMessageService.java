package org.example.messages_application.services;

import org.example.messages_application.dto.in.message.MessageCreateDto;
import org.example.messages_application.dto.in.message.MessageDto;
import org.example.messages_application.dto.out.message.MessageAmountDto;

import java.util.List;

public interface IMessageService {

    MessageDto sendMessage(Long chatId, MessageCreateDto messageCreateDto);

    void readMessage(Long chatId, Long userId);

    void deleteMessage(Long chatId, Long messageId);

    List<MessageAmountDto> getSentMessagesByTime(Long userId);

}
