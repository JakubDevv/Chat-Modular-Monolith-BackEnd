package org.example.messages_infrastructure.services;

import lombok.RequiredArgsConstructor;
import models.Group;
import models.GroupUser;
import models.id.GroupUserID;
import org.example.messages_application.repository.IGroupRepository;
import org.example.messages_infrastructure.entities.GroupEntity;
import org.example.messages_infrastructure.entities.GroupUserEntity;
import org.example.messages_infrastructure.entities.composedKey.GroupUserIDKey;
import org.example.messages_infrastructure.exceptions.GroupNotFoundException;
import org.example.messages_infrastructure.exceptions.GroupUserNotFoundException;
import org.example.messages_infrastructure.mapper.MessagesEntityDomainMapper;
import org.example.messages_infrastructure.repository.GroupRepositoryDB;
import org.example.messages_infrastructure.repository.GroupUserRepositoryDB;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class GroupServiceImpl implements IGroupRepository {

    private final GroupUserRepositoryDB groupUserRepositoryDB;
    private final GroupRepositoryDB groupRepositoryDB;
    private final MessagesEntityDomainMapper messagesEntityDomainMapper;

    @Override
    public Long save(Group group) {
        return groupRepositoryDB.save(messagesEntityDomainMapper.groupDomainToEntity(group)).getId();
    }

    @Override
    public Group findById(Long groupId) {
        GroupEntity groupEntity = groupRepositoryDB.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        return messagesEntityDomainMapper.groupEntityToDomain(groupEntity);
    }

    @Override
    public void update(GroupUser groupUser) {
        groupUserRepositoryDB.save(messagesEntityDomainMapper.groupUserDomainToEntity(groupUser));
    }

    @Override
    public void updateAll(List<GroupUser> groupUsers) {
        groupUserRepositoryDB.saveAll(
                groupUsers.stream().map(messagesEntityDomainMapper::groupUserDomainToEntity).toList()
        );
    }

    @Override
    public void remove(GroupUserID groupUserId) {
        groupUserRepositoryDB.deleteById(messagesEntityDomainMapper.groupUserIdDomainToEntity(groupUserId));
    }

    @Override
    public GroupUser findById(GroupUserID groupUserId) {
        GroupUserIDKey groupUserIDEntityKey = messagesEntityDomainMapper.groupUserIdDomainToEntity(groupUserId);
        GroupUserEntity groupUserEntity = groupUserRepositoryDB.findById(groupUserIDEntityKey).orElseThrow(() -> new GroupUserNotFoundException(groupUserIDEntityKey));
        return messagesEntityDomainMapper.groupUserEntityToDomain(groupUserEntity);
    }

    @Override
    public void save(GroupUser groupUser) {
        groupUserRepositoryDB.save(messagesEntityDomainMapper.groupUserDomainToEntity(groupUser));
    }

    @Override
    public List<GroupUser> findAllGroupsById(Long userId) {
        return groupUserRepositoryDB.findAllByUserId(userId).stream().map(messagesEntityDomainMapper::groupUserEntityToDomain).toList();
    }

    @Override
    public List<GroupUser> findAllUsersById(Long groupId) {
        return groupUserRepositoryDB.findAllByChatId(groupId).stream().map(messagesEntityDomainMapper::groupUserEntityToDomain).toList();
    }

    @Override
    public Long findUserByGroupId(Long groupId, Long userId) {
        return groupUserRepositoryDB.findUserByGroupId(groupId, userId).orElseThrow(() -> new GroupUserNotFoundException(groupId));
    }

}
