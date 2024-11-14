package org.example.messages_infrastructure.entities;

import enums.GroupNotificationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sender_id;

    private String content;

    private boolean image;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "response_id")
    private MessageEntity response_id;

    @Enumerated(value = EnumType.STRING)
    private GroupNotificationType notification_type;

    public MessageEntity(Long sender_id, String content, boolean image, LocalDateTime sentAt, Boolean deleted, GroupEntity group, MessageEntity response_id, GroupNotificationType notification_type) {
        this.sender_id = sender_id;
        this.content = content;
        this.image = image;
        this.sentAt = sentAt;
        this.deleted = deleted;
        this.group = group;
        this.response_id = response_id;
        this.notification_type = notification_type;
    }

    public MessageEntity() {
    }
}
