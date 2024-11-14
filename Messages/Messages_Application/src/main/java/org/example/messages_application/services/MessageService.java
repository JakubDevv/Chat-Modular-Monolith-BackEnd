package org.example.messages_application.services;

import models.GroupUser;
import models.Message;
import models.id.GroupUserID;
import org.example.messages_application.events.NewMessageEvent;
import org.example.user_application.dto.users.out.FullNameDto;
import org.example.messages_application.dto.in.message.MessageCreateDto;
import org.example.messages_application.dto.in.message.MessageDto;
import org.example.messages_application.dto.out.message.MessageAmountDto;
import org.example.messages_application.repository.IGroupRepository;
import org.example.messages_application.repository.IMessageRepository;
import org.example.user_application.services.IUserService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {

    private final IMessageRepository messageRepository;
    private final IGroupRepository groupRepository;
    private final IUserService userService;
    private final IGroupService groupService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MessageService(IMessageRepository messageRepository, IGroupRepository groupRepository, IUserService userService, IGroupService groupService, ApplicationEventPublisher applicationEventPublisher) {
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.groupService = groupService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public MessageDto sendMessage(Long chatId, MessageCreateDto messageCreateDto) {
        Message message = messageRepository.saveMessage(new Message(messageCreateDto.sender_id(), messageCreateDto.content(), messageCreateDto.media(), chatId, messageCreateDto.response_id()));
        FullNameDto user = userService.getUsersFullNameById(messageCreateDto.sender_id());
        MessageDto messageDto = new MessageDto(
                        message.getId().value(),
                        message.getContent().value(),
                        message.isImage(),
                        user.firstName(),
                        user.lastName(),
                        messageCreateDto.sender_id(),
                        message.getSentAt(),
                        messageCreateDto.response_id().equals(0L) ? null : messageRepository.findById(messageCreateDto.response_id()).getId().value(),
                        messageCreateDto.response_id().equals(0L) ? null : userService.getUsersFullNameById(messageRepository.findById(messageCreateDto.response_id()).getSenderId().value()).firstName(),
                        messageCreateDto.response_id().equals(0L) ? null : userService.getUsersFullNameById(messageRepository.findById(messageCreateDto.response_id()).getSenderId().value()).lastName(),
                        messageCreateDto.response_id().equals(0L) ? null : messageRepository.findById(messageCreateDto.response_id()).getSenderId().value(),
                        messageCreateDto.response_id().equals(0L) ? null : messageRepository.findById(messageCreateDto.response_id()).getSentAt(),
                        messageCreateDto.response_id().equals(0L) ? null : messageRepository.findById(messageCreateDto.response_id()).getContent().value(),
                        messageCreateDto.response_id().equals(0L) ? null : messageRepository.findById(messageCreateDto.response_id()).getDeleted(),
                        message.getDeleted(),
                        List.of(),
                        chatId,
                        null);
        groupService.appendUnreadMessagesToAllUsersByChatId(messageDto);
        return null;
    }

    @Override
    public void readMessage(Long chatId, Long userId) {
        GroupUser groupUser = groupRepository.findById(new GroupUserID(userId, chatId));
        groupUser.readAllMessages();
        groupRepository.save(groupUser);
    }

    @Override
    public void deleteMessage(Long chatId, Long messageId) {
        Message message = messageRepository.findById(messageId);
        message.deleteMessage();
        messageRepository.saveMessage(message);
    }

    @Override
    public List<MessageAmountDto> getSentMessagesByTime(Long userId) {
        return messageRepository.getSentMessagesByTime(userId);
    }

}
