package models.id;

import vo.GroupId;
import vo.UserId;

public class GroupUserID {

    private final UserId user_id;

    private final GroupId group_id;

    public GroupUserID(Long user_id, Long group_id) {
        this.user_id = new UserId(user_id);
        this.group_id = new GroupId(group_id);
    }

    public UserId getUser_id() {
        return user_id;
    }

    public GroupId getGroup_id() {
        return group_id;
    }

}
