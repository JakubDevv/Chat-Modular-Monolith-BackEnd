package org.example.messages_application.services;

import org.example.messages_application.dto.in.chat.ChatCreateDto;
import org.example.messages_application.dto.in.message.MessageDto;
import org.example.messages_application.dto.out.chat.ChatDto;
import org.example.messages_application.dto.out.chat.ChatShortDto;

import java.io.InputStream;
import java.util.List;

public interface IGroupService {

    List<ChatShortDto> getChats(Long userId);
    ChatDto getChatById(Long userId, Long chatId);
    byte[] getChatPictureById(Long chatId);
    Long createChat(Long userId, ChatCreateDto chatCreateDto);
    void removeChat(Long userId, Long chatId);
    void removeUserFromChat(Long userId, Long chatUserId, Long chatId);
    void sendFile(Long userId, Long chatId, InputStream file);
    byte[] getFile(Long userId, Long messageId);
    void addUserToChat(Long userId, Long chatUserId, Long chatId);
    void setChatPicture(Long userId, Long chatId, InputStream file);
    void leaveChat(Long userId, Long chatId);
    void appendUnreadMessagesToAllUsersByChatId(MessageDto messageDto);
}
