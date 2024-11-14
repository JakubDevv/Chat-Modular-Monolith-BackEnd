package org.example.messages_application.dto.in.message;

import enums.GroupNotificationType;

import java.time.LocalDateTime;
import java.util.List;

public class MessageDto {

    public Long id;
    public String content;
    public boolean photo;
    public String firstName;
    public String lastName;
    public Long userId;
    public LocalDateTime dateTime;
    public Long response_id;
    public String response_firstName;
    public String response_lastName;
    public Long response_user_id;
    public LocalDateTime response_dateTime;
    public String response_content;
    public Boolean response_deleted;
    public Boolean deleted;
    public List<Long> ids;
    public Long chat_id;
    public GroupNotificationType notification_type;

    public MessageDto(Long id, String content, boolean photo, String firstName, String lastName, Long userId, LocalDateTime dateTime, Long response_id, String response_firstName, String response_lastName, Long response_user_id, LocalDateTime response_dateTime, String response_content, Boolean response_deleted, Boolean deleted, List<Long> ids, Long chat_id, GroupNotificationType notification_type) {
        this.id = id;
        this.content = content;
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.dateTime = dateTime;
        this.response_id = response_id;
        this.response_firstName = response_firstName;
        this.response_lastName = response_lastName;
        this.response_user_id = response_user_id;
        this.response_dateTime = response_dateTime;
        this.response_content = response_content;
        this.response_deleted = response_deleted;
        this.deleted = deleted;
        this.ids = ids;
        this.chat_id = chat_id;
        this.notification_type = notification_type;
    }

    public MessageDto(Long id, String content, boolean photo, String firstName, String lastName, Long userId, LocalDateTime dateTime, Boolean deleted, List<Long> ids, Long chat_id, GroupNotificationType notification_type) {
        this.id = id;
        this.content = content;
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.dateTime = dateTime;
        this.deleted = deleted;
        this.ids = ids;
        this.chat_id = chat_id;
        this.notification_type = notification_type;
    }

    public MessageDto(Long id, String content, String firstName, String lastName, Long userId, LocalDateTime dateTime, GroupNotificationType notification_type, Long chat_id) {
        this.id = id;
        this.content = content;
        this.photo = false;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.dateTime = dateTime;
        this.ids = List.of();
        this.notification_type = notification_type;
        this.chat_id = chat_id;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isPhoto() {
        return photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getResponse_id() {
        return response_id;
    }

    public String getResponse_firstName() {
        return response_firstName;
    }

    public String getResponse_lastName() {
        return response_lastName;
    }

    public Long getResponse_user_id() {
        return response_user_id;
    }

    public LocalDateTime getResponse_dateTime() {
        return response_dateTime;
    }

    public String getResponse_content() {
        return response_content;
    }

    public Boolean getResponse_deleted() {
        return response_deleted;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public List<Long> getIds() {
        return ids;
    }

    public GroupNotificationType getNotification_type() {
        return notification_type;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
