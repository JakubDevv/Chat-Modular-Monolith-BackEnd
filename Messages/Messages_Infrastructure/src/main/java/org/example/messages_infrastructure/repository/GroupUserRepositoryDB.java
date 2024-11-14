package org.example.messages_infrastructure.repository;

import org.example.messages_infrastructure.entities.GroupUserEntity;
import org.example.messages_infrastructure.entities.composedKey.GroupUserIDKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupUserRepositoryDB extends JpaRepository<GroupUserEntity, GroupUserIDKey> {

    @Query("SELECT gu.groupUserID.user_id FROM GroupUserEntity gu where gu.groupUserID.group_id.id = :groupId AND gu.groupUserID.user_id != :userId")
    Optional<Long> findUserByGroupId(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Query("SELECT gu FROM GroupUserEntity gu where gu.groupUserID.group_id.id = ?1")
    List<GroupUserEntity> findAllByChatId(Long chatId);

    @Query("SELECT gu FROM GroupUserEntity gu where gu.groupUserID.user_id = ?1")
    List<GroupUserEntity> findAllByUserId(Long userId);
}
