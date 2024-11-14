package aggregates;

import vo.GroupId;
import vo.UserId;

import java.util.List;

public class GroupUsers {

    private GroupId groupId;

    private List<UserId> users;

    public GroupUsers(GroupId groupId, List<UserId> users) {
        this.groupId = groupId;
        this.users = users;
    }

    public GroupId getGroupId() {
        return groupId;
    }

    public List<UserId> getUsers() {
        return users;
    }
}
