package models;

import enums.GroupNotificationType;
import vo.*;

import java.time.LocalDateTime;

public class Message {

    private MessageId id;

    private UserId senderId;

    private MessageContent content;

    private boolean image;

    private LocalDateTime sentAt;

    private Boolean deleted;

    private GroupId groupId;

    private ResponseId responseId;

    private GroupNotificationType notificationType;

    public Message(Long senderId, String content, boolean image, Long groupId, Long responseId) {
        this.senderId = new UserId(senderId);
        this.content = new MessageContent(content);
        this.image = image;
        this.sentAt = LocalDateTime.now();
        this.deleted = false;
        this.groupId = new GroupId(groupId);
        this.responseId = new ResponseId(responseId);
    }

    public Message(Long senderId, String content, Long groupId, GroupNotificationType notificationType) {
        this.senderId = new UserId(senderId);
        this.content = new MessageContent(content);
        this.image = false;
        this.sentAt = LocalDateTime.now();
        this.deleted = false;
        this.groupId = new GroupId(groupId);
        this.notificationType = notificationType;
    }

    public Message(Long id, Long senderId, String content, boolean image, LocalDateTime sentAt, Boolean deleted, Long groupId, Long responseId, GroupNotificationType notificationType) {
        this.id = new MessageId(id);
        this.senderId = new UserId(senderId);
        this.content = new MessageContent(content);
        this.image = image;
        this.sentAt = sentAt;
        this.deleted = deleted;
        this.groupId = new GroupId(groupId);
        this.responseId = new ResponseId(responseId);
        this.notificationType = notificationType;
    }

    public boolean isNotification() {
        return notificationType != null;
    }

    public void deleteMessage() {
        this.deleted = true;
    }

    public MessageId getId() {
        return id;
    }

    public UserId getSenderId() {
        return senderId;
    }

    public MessageContent getContent() {
        return content;
    }

    public boolean isImage() {
        return image;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public GroupId getGroupId() {
        return groupId;
    }

    public ResponseId getResponseId() {
        return responseId;
    }

    public GroupNotificationType getNotificationType() {
        return notificationType;
    }
}
