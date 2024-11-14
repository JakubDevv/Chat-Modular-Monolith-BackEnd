package org.example.messages_application.services;

import enums.FriendStatus;
import enums.GroupNotificationType;
import enums.GroupRole;
import models.Group;
import models.GroupUser;
import models.Message;
import models.User;
import models.id.GroupUserID;
import org.example.messages_application.dto.ChatUserDto;
import org.example.messages_application.dto.in.chat.ChatCreateDto;
import org.example.messages_application.dto.in.message.MessageDto;
import org.example.messages_application.dto.out.chat.ChatDto;
import org.example.messages_application.dto.out.chat.ChatShortDto;
import org.example.messages_application.events.NewMessageEvent;
import org.example.messages_application.events.UserAddedToGroupEvent;
import org.example.messages_application.events.UserRemovedFromGroupEvent;
import org.example.messages_application.mapper.MessageMapper;
import org.example.messages_application.repository.IGroupRepository;
import org.example.messages_application.repository.IMessageRepository;
import org.example.shared.s3.S3Service;
import org.example.user_application.dto.users.out.FullNameDto;
import org.example.user_application.services.IFriendService;
import org.example.user_application.services.IUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService implements IGroupService {

    private final IGroupRepository groupRepository;
    private final IMessageRepository messageRepository;
    private final IUserService userService;
    private final IFriendService friendService;
    private final MessageMapper messageMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final S3Service s3Service;

    public GroupService(IGroupRepository groupRepository, IMessageRepository messageRepository, IUserService userService, IFriendService friendService, MessageMapper messageMapper, ApplicationEventPublisher eventPublisher, S3Service s3Service) {
        this.groupRepository = groupRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.friendService = friendService;
        this.messageMapper = messageMapper;
        this.eventPublisher = eventPublisher;
        this.s3Service = s3Service;
    }


    @Override
    public List<ChatShortDto> getChats(Long userId) {
        return groupRepository.findAllGroupsById(userId).stream()
                .map(group -> groupRepository.findById(group.getGroupID()))
                .map(group -> messageMapper.chatToChatShortDTO(group, userId))
                .sorted((a, b) -> {
            if (a.lastMessageTime() == null && b.lastMessageTime() == null) {
                return 0;
            } else if (a.lastMessageTime() == null) {
                return 1;
            } else if (b.lastMessageTime() == null) {
                return -1;
            } else {
                return b.lastMessageTime().compareTo(a.lastMessageTime());
            }
        }).toList();
    }


    @Override
    public ChatDto getChatById(Long userId, Long chatId) {
        Group group = groupRepository.findById(chatId);
        GroupUser user = groupRepository.findById(new GroupUserID(userId, chatId));
        String chatName = "";
        if(group.getName() == null){
            Long user1 = groupRepository.findUserByGroupId(chatId, userId);
            FullNameDto usersFullNameById = userService.getUsersFullNameById(user1);
            chatName = usersFullNameById.firstName() + " " + usersFullNameById.lastName();
        }else {
            chatName = group.getName();
        }
        List<ChatUserDto> users = groupRepository.findAllUsersById(chatId).stream().map(userr -> {
            FriendStatus friends = friendService.getFriendshipStatus(userr.getUserID(), userId);
            User userrr = userService.getUserById(userr.getUserID());
            return new ChatUserDto(userrr.getId().value(), userrr.getFullName().firstName(), userrr.getFullName().lastName(), friends, messageRepository.countMessagesInChatByUserId(chatId, userrr.getId().value()), friendService.countCommonFriends(userrr.getId().value(), userId), userrr.getUserStatus(), userrr.getBgColor().value());
        }).toList();
        return new ChatDto(
                group.getId(),
                chatName,
                group.isIsduo(),
                new ArrayList<>(),
                messageRepository.countMessagesInChat(chatId),
                group.getCreated(),
                user.getRole(),
                users,
                getMessagesByChatId(group.getId(), userId)
        );
    }

    public List<MessageDto> getMessagesByChatId(Long chatId, Long userId) {
        List<MessageDto> messages = messageRepository.findAllByGroupIdOrderBySentAt(chatId).stream().map((message) -> {
            if(message.getResponseId().value()==null){
                FullNameDto fullName = userService.getUsersFullNameById(message.getSenderId().value());
                return messageMapper.messageToMessageDTO(message, fullName, null, null);
            } else {
                Message response = messageRepository.findById(message.getResponseId().value());
                FullNameDto fullName = userService.getUsersFullNameById(message.getSenderId().value());
                FullNameDto fullNameResponse = userService.getUsersFullNameById(response.getSenderId().value());
                return messageMapper.messageToMessageDTO(message, fullName, response, fullNameResponse);
            }
        }).toList();
        groupRepository.findAllUsersById(chatId).forEach(groupUser -> {
            if(!messages.isEmpty() && groupUser.getUnread_messages() != messages.size()){
                MessageDto message = messages.get(messages.size() - Math.toIntExact(groupUser.getUnread_messages()) - 1);
                List<Long> ids = new ArrayList<>(message.getIds());
                ids.add(groupUser.getUserID());
                message.setIds(ids);
            }
        });
        return messages;
    }

    @Override
    public byte[] getChatPictureById(Long chatId) {
        try {
            return s3Service.getObject("ChatImages/" + chatId);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Long createChat(Long userId, ChatCreateDto chatCreateDto) {
        Group group = new Group(chatCreateDto.name(), false);
        Long groupId = groupRepository.save(group);
        groupRepository.save(new GroupUser(new GroupUserID(userId, groupId), GroupRole.OWNER, 0L));
        List<String> usersFullName = new ArrayList<>();
        chatCreateDto.ids().stream().forEach(id -> {
            FullNameDto userFullName = userService.getUsersFullNameById(id);
            usersFullName.add(userFullName.toString());
            groupRepository.save(new GroupUser(new GroupUserID(id, groupId), GroupRole.USER, 1L));
            eventPublisher.publishEvent(new UserAddedToGroupEvent(userId, id, chatCreateDto.name()));
        });
        messageRepository.saveMessage(new Message(userId, String.join(", ", usersFullName), groupId, GroupNotificationType.GROUP_CREATED));
        return groupId;
    }

    @Override
    public void removeChat(Long userId, Long chatId) {
        Group group = groupRepository.findById(chatId);
        if(group.isIsduo()){

        }
    }

    @Override
    public void removeUserFromChat(Long userId, Long chatUserId, Long chatId) {
        Group group = groupRepository.findById(chatId);
        FullNameDto userFullName = userService.getUsersFullNameById(chatUserId);
        eventPublisher.publishEvent(new UserRemovedFromGroupEvent(userId, chatUserId, group.getName()));
        groupRepository.remove(new GroupUserID(userId, chatId));
        Message save = messageRepository.saveMessage(new Message(userId, userFullName.firstName() + " " + userFullName.lastName(), chatId, GroupNotificationType.GROUP_LEFT));
        appendUnreadMessagesToAllUsersByChatId(messageMapper.messageNotificationToMessageDTO(save, userFullName));
    }

    @Override
    public void sendFile(Long userId, Long chatId, InputStream file) {
        Message message = new Message(userId, "Sent Message", true, chatId, null);
        Message save = messageRepository.saveMessage(message);
        try {
            s3Service.putObject("Messages/" + save.getId(), file.readAllBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        FullNameDto usersFullName = userService.getUsersFullNameById(chatId);
        appendUnreadMessagesToAllUsersByChatId(messageMapper.messageFileToMessageDTO(save, usersFullName));
    }

    @Override
    public byte[] getFile(Long userId, Long messageId) {
        try {
            return s3Service.getObject("Messages/" + messageId);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void addUserToChat(Long userId, Long chatUserId, Long chatId) {
        Group group = groupRepository.findById(chatId);
        FullNameDto userFullName = userService.getUsersFullNameById(userId);
        GroupUser groupUser = new GroupUser(new GroupUserID(chatUserId, chatId), messageRepository.countMessagesInChat(chatId));
        groupRepository.save(groupUser);
        eventPublisher.publishEvent(new UserAddedToGroupEvent(userId, chatUserId, group.getName()));
        Message message = messageRepository.saveMessage(new Message(userId, userFullName.firstName() + " " + userFullName.lastName(), chatId, GroupNotificationType.GROUP_ADDED));
        appendUnreadMessagesToAllUsersByChatId(messageMapper.messageNotificationToMessageDTO(message, userFullName));
    }

    @Override
    public void setChatPicture(Long userId, Long chatId, InputStream file) {
        try {
            s3Service.putObject("ChatImages/" + chatId, file.readAllBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void leaveChat(Long userId, Long chatId) {
        FullNameDto userFullName = userService.getUsersFullNameById(userId);
        groupRepository.remove(new GroupUserID(userId, chatId));
        Message message = new Message(userId, userFullName.firstName() + " " + userFullName.lastName(), chatId, GroupNotificationType.GROUP_LEFT);
        messageRepository.saveMessage(message);
        appendUnreadMessagesToAllUsersByChatId(messageMapper.messageNotificationToMessageDTO(message, userFullName));
    }

    @Override
    public void appendUnreadMessagesToAllUsersByChatId(MessageDto messageDto) {
        groupRepository.findAllUsersById(messageDto.getChat_id()).forEach(userr -> {
            userr.appendUserUnreadMessagesByOne();
            groupRepository.save(userr);
            eventPublisher.publishEvent(new NewMessageEvent(this, messageDto, userr.getUserID()));
        });
    }

}
