package org.example.notifications.repository;

import org.example.notifications.dto.NotificationDto;
import org.example.notifications.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n WHERE n.receiver_id = ?1")
    List<NotificationDto> getNotificationsByUserId(Long userId);

}
