package org.example.messages_infrastructure.repository;

import org.example.messages_infrastructure.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepositoryDB extends JpaRepository<GroupEntity, Long> {

    @Query(value = """
            SELECT COUNT(*) 
            FROM groups_users gu1 
            JOIN groups_users gu2 ON gu1.group_id = gu2.group_id 
            JOIN groups g ON g.id = gu1.group_id 
            WHERE gu1.user_id = :userId1 AND gu2.user_id = :userId2 AND g.isduo = false
            """, nativeQuery = true)
    Long countCommonGroups(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query(value = """
            SELECT COUNT(*) 
            FROM groups_users gu1 
            WHERE gu1.user_id = :userId1 AND gu1.isduo = false
            """, nativeQuery = true)
    Long countGroups(@Param("userId1") Long userId);
}
