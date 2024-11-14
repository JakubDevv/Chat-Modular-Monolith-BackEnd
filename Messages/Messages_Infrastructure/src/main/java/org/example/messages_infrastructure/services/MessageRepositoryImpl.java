package org.example.messages_infrastructure.services;

import models.Message;
import org.example.messages_application.dto.out.message.MessageAmountDto;
import org.example.messages_application.repository.IMessageRepository;
import org.example.messages_infrastructure.entities.GroupEntity;
import org.example.messages_infrastructure.entities.MessageEntity;
import org.example.messages_infrastructure.mapper.MessagesEntityDomainMapper;
import org.example.messages_infrastructure.repository.GroupRepositoryDB;
import org.example.messages_infrastructure.repository.MessageRepositoryDB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageRepositoryImpl implements IMessageRepository {

    private final MessageRepositoryDB messageRepositoryDb;
    private final MessagesEntityDomainMapper messagesEntityDomainMapper;
    private final GroupRepositoryDB groupRepositoryDB;

    public MessageRepositoryImpl(MessageRepositoryDB messageRepositoryDb, MessagesEntityDomainMapper messagesEntityDomainMapper, GroupRepositoryDB groupRepositoryDB) {
        this.messageRepositoryDb = messageRepositoryDb;
        this.messagesEntityDomainMapper = messagesEntityDomainMapper;
        this.groupRepositoryDB = groupRepositoryDB;
    }

    @Override
    public Message saveMessage(Message message) {
        return messagesEntityDomainMapper.messageEntityToDomain(
                messageRepositoryDb.save(
                        messagesEntityDomainMapper.messageDomainToEntity(message)
                )
        );
    }

    @Override
    public Message findById(Long id) {
        MessageEntity messageEntity = messageRepositoryDb.findById(id).orElse(null);
        return messagesEntityDomainMapper.messageEntityToDomain(messageEntity);
    }

    @Override
    public List<MessageAmountDto> getSentMessagesByTime(Long userId) {
        return messageRepositoryDb.getSentMessagesByTime(userId);
    }

    @Override
    public Long countMessagesInChat(Long chatId) {
        return messageRepositoryDb.countAllByGroup(chatId);
    }

    @Override
    public Long countMessagesInChatByUserId(Long chatId, Long userId) {
        return messageRepositoryDb.countAllByGroupAndUser(chatId, userId);
    }

    @Override
    public List<Message> findAllByGroupIdOrderBySentAt(Long groupId) {
        GroupEntity group =groupRepositoryDB.findById(groupId).orElse(null);
        return messageRepositoryDb.findAllByGroupOrderBySentAt(group).stream().map(messagesEntityDomainMapper::messageEntityToDomain).toList();
    }

    @Override
    public Message findLastMessageByGroupId(Long groupId) {
        GroupEntity group =groupRepositoryDB.findById(groupId).orElse(null);
        MessageEntity message = messageRepositoryDb.findFirstByGroupOrderBySentAtDesc(group).orElse(null);
        if(message == null)
            return null;
        else
            return messagesEntityDomainMapper.messageEntityToDomain(message);
    }

}