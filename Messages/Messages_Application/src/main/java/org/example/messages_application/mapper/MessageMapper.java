package org.example.messages_application.mapper;

import models.Group;
import models.GroupUser;
import models.Message;
import models.User;
import models.id.GroupUserID;
import org.example.user_application.dto.users.out.FullNameDto;
import org.example.messages_application.dto.in.message.MessageDto;
import org.example.messages_application.dto.out.chat.ChatShortDto;
import org.example.messages_application.repository.IGroupRepository;
import org.example.messages_application.repository.IMessageRepository;
import org.example.user_application.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageMapper {

    private final IGroupRepository groupRepository;
    private final IMessageRepository messageRepository;
    private final IUserService userService;

    public MessageMapper(IMessageRepository messageRepository, IUserService userService, IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public MessageDto messageNotificationToMessageDTO(Message message, FullNameDto fullName) {
        return new MessageDto(
                message.getId().value(),
                message.getContent().value(),
                fullName.firstName(),
                fullName.lastName(),
                message.getSenderId().value(),
                message.getSentAt(),
                message.getNotificationType(),
                message.getGroupId().value()
        );
    }

    public MessageDto messageToMessageDTO(Message message, FullNameDto fullName, Message responseMessage, FullNameDto responseFullName)  {
        return new MessageDto(
                message.getId().value(),
                message.getContent().value(),
                message.isImage(),
                fullName.firstName(),
                fullName.lastName(),
                message.getSenderId().value(),
                message.getSentAt(),
                responseMessage == null ? null : responseMessage.getId().value(),
                responseMessage == null ? null : responseFullName.firstName(),
                responseMessage == null ? null : responseFullName.lastName(),
                responseMessage == null ? null : responseMessage.getSenderId().value(),
                responseMessage == null ? null : responseMessage.getSentAt(),
                responseMessage == null ? null : responseMessage.getContent().value(),
                responseMessage == null ? null : responseMessage.getDeleted(),
                message.getDeleted(),
                List.of(),
                message.getGroupId().value(),
                message.getNotificationType());
    }

    public ChatShortDto chatToChatShortDTO(Group group, Long userId){
        Message message = messageRepository.findLastMessageByGroupId(group.getId());
        User friend = new User();
        if(group.isIsduo()){
            friend = userService.getUserById(groupRepository.findUserByGroupId(group.getId(), userId));
        }
        return new ChatShortDto(
                group.getId(),
                message != null ? message.getSentAt() : null,
                message != null ? message.getContent().value() : null,
                groupRepository.findById(new GroupUserID(userId, group.getId())).getUnread_messages(),
                message != null ? message.getSenderId().value() : null,
                message != null ? userService.getUsersFullNameById(message.getSenderId().value()).firstName() + " " + userService.getUsersFullNameById(message.getSenderId().value()).lastName()  : null,
                group.isIsduo() ? friend.getFullName().firstName() + " " + friend.getFullName().lastName() : group.getName(),
                group.isIsduo() ? friend.getBgColor().value() : "",
                friend.getId() == null ? null : friend.getId().value(),
                group.isIsduo(),
                userService.countOnlineUsers(groupRepository.findAllUsersById(group.getId()).stream().map(GroupUser::getUserID).toList()) - 1,
                message != null ? message.getNotificationType() : null);
    }

    public MessageDto messageFileToMessageDTO(Message message, FullNameDto user) {
        return new MessageDto(
                message.getId().value(),
                message.getContent().value(),
                message.isImage(),
                user.firstName(),
                user.lastName(),
                message.getSenderId().value(),
                message.getSentAt(),
                message.getDeleted(),
                List.of(),
                message.getGroupId().value(),
                null);
    }

}
