package org.example.messages_infrastructure.mapper;

import models.Group;
import models.GroupUser;
import models.Message;
import models.id.GroupUserID;
import org.example.messages_infrastructure.entities.GroupEntity;
import org.example.messages_infrastructure.entities.GroupUserEntity;
import org.example.messages_infrastructure.entities.MessageEntity;
import org.example.messages_infrastructure.entities.composedKey.GroupUserIDKey;
import org.example.messages_infrastructure.exceptions.GroupNotFoundException;
import org.example.messages_infrastructure.repository.GroupRepositoryDB;
import org.example.messages_infrastructure.repository.MessageRepositoryDB;
import org.springframework.stereotype.Service;

@Service
public class MessagesEntityDomainMapper {

    private final GroupRepositoryDB groupRepositoryDB;
    private final MessageRepositoryDB messageRepositoryDB;

    public MessagesEntityDomainMapper(GroupRepositoryDB groupRepositoryDB, MessageRepositoryDB messageRepositoryDB) {
        this.groupRepositoryDB = groupRepositoryDB;
        this.messageRepositoryDB = messageRepositoryDB;
    }

    public GroupUser groupUserEntityToDomain(GroupUserEntity groupUserEntity){
           return new GroupUser(
                   new GroupUserID(groupUserEntity.getGroupUserID().getUser_id(), groupUserEntity.getGroupUserID().getGroup_id().getId()),
                   groupUserEntity.getUnread_messages(),
                   groupUserEntity.getRole(),
                   groupUserEntity.getAdded()
           );
    }

    public Message messageEntityToDomain(MessageEntity messageEntity){
        return new Message(
                messageEntity.getId(),
                messageEntity.getSender_id(),
                messageEntity.getContent(),
                messageEntity.isImage(),
                messageEntity.getSentAt(),
                messageEntity.getDeleted(),
                messageEntity.getGroup().getId(),
                messageEntity.getResponse_id() == null ? null : messageEntity.getResponse_id().getId(),
                messageEntity.getNotification_type()
        );
    }

    public Group groupEntityToDomain(GroupEntity groupEntity){
        return new Group(
                groupEntity.getId(),
                groupEntity.getName(),
                groupEntity.getCreated(),
                groupEntity.isIsduo()
        );
    }

    public GroupUserID groupUserIdKeyToDomain(GroupUserIDKey groupUserIDKey){
        return new GroupUserID(
                groupUserIDKey.getUser_id(),
                groupUserIDKey.getGroup_id().getId()
        );
    }

    public GroupUserEntity groupUserDomainToEntity(GroupUser groupUser){
        GroupEntity group = groupRepositoryDB.findById(groupUser.getGroupID()).orElseThrow(() -> new GroupNotFoundException(groupUser.getGroupID()));
        return new GroupUserEntity(
                groupUser.getUserID(),
                group,
                groupUser.getUnread_messages(),
                groupUser.getRole(),
                groupUser.getAdded()
        );
    }

    public MessageEntity messageDomainToEntity(Message message){
        GroupEntity group = groupRepositoryDB.findById(message.getGroupId().value()).orElseThrow(() -> new GroupNotFoundException(message.getGroupId().value()));
        if(message.getResponseId().value().equals(0L)){
            return new MessageEntity(
                    message.getSenderId().value(),
                    message.getContent().value(),
                    message.isImage(),
                    message.getSentAt(),
                    message.getDeleted(),
                    group,
                    null,
                    message.getNotificationType()
            );
        } else {
            MessageEntity response = messageRepositoryDB.findById(message.getResponseId().value()).orElseThrow(() -> new GroupNotFoundException(message.getResponseId().value()));
            return new MessageEntity(
                    message.getSenderId().value(),
                    message.getContent().value(),
                    message.isImage(),
                    message.getSentAt(),
                    message.getDeleted(),
                    group,
                    response,
                    message.getNotificationType()
            );
        }
    }

    public GroupEntity groupDomainToEntity(Group group){
        return new GroupEntity(
                group.getId(),
                group.getName(),
                group.getCreated(),
                group.isIsduo()
        );
    }

    public GroupUserIDKey groupUserIdDomainToEntity(GroupUserID groupUserID){
        GroupEntity groupEntity = groupRepositoryDB.findById(groupUserID.getGroup_id().value()).orElseThrow(() -> new GroupNotFoundException(groupUserID.getGroup_id().value()));
        return new GroupUserIDKey(
                groupUserID.getUser_id().value(),
                groupEntity
        );
    }
}
