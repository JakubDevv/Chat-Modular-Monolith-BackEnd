package org.example.messages_infrastructure.repository;

import org.example.messages_application.dto.out.message.MessageAmountDto;
import org.example.messages_infrastructure.entities.GroupEntity;
import org.example.messages_infrastructure.entities.MessageEntity;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Order(1)
@Repository
public interface MessageRepositoryDB extends JpaRepository<MessageEntity, Long> {

    @Query("""
            SELECT new org.example.messages_application.dto.out.message.MessageAmountDto(DATE_TRUNC('month', m.sentAt), COUNT(*)) 
            FROM MessageEntity m 
            WHERE m.sender_id=?1 GROUP BY DATE_TRUNC('month', m.sentAt)
            """)
    List<MessageAmountDto> getSentMessagesByTime(Long id);

    @Query("SELECT COUNT(*) FROM MessageEntity m WHERE m.group.id=?1")
    Long countAllByGroup(Long groupId);

    @Query("SELECT COUNT(*) FROM MessageEntity m WHERE m.group.id = ?1 AND m.sender_id = ?2")
    Long countAllByGroupAndUser(Long groupId, Long chatId);

    List<MessageEntity> findAllByGroupOrderBySentAt(GroupEntity group);

    Optional<MessageEntity> findFirstByGroupOrderBySentAtDesc(GroupEntity group);
}
