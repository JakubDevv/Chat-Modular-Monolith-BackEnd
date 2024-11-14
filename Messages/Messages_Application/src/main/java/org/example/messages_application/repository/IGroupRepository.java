package org.example.messages_application.repository;

import models.Group;
import models.GroupUser;
import models.id.GroupUserID;

import java.util.List;

public interface IGroupRepository {

    Long save(Group group);
    Group findById(Long groupId);

    void update(GroupUser groupUser);
    void updateAll(List<GroupUser> groupUsers);
    void remove(GroupUserID groupUserId);
    GroupUser findById(GroupUserID groupUserId);
    void save(GroupUser groupUser);

    List<GroupUser> findAllGroupsById(Long userId);
    List<GroupUser> findAllUsersById(Long groupId);

    Long findUserByGroupId(Long groupId, Long userId);

}
