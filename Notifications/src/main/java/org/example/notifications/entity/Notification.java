package org.example.notifications.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.notifications.consts.NotificationType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sender_id;

    private Long receiver_id;

    private LocalDateTime sent_time;

    private String group_name;

    @Enumerated(value = EnumType.STRING)
    private NotificationType notification_type;

    public Notification(Long sender, Long receiver, NotificationType notification_type) {
        this.sender_id = sender;
        this.receiver_id = receiver;
        this.sent_time = LocalDateTime.now();
        this.notification_type = notification_type;
    }

    public Long getReceiver() {
        return receiver_id;
    }
}