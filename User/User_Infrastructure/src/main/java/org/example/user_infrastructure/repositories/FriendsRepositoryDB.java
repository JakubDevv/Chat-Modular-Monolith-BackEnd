package org.example.user_infrastructure.repositories;

import enums.FriendStatus;
import models.Friend;
import org.example.user_application.dto.friends.FriendsCountriesDto;
import org.example.user_application.dto.interests.InterestStatsDto;
import org.example.user_infrastructure.entities.FriendsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendsRepositoryDB extends JpaRepository<FriendsEntity, Integer> {

    @Query(value = """
            SELECT COUNT(*) 
            FROM ( 
            SELECT CASE WHEN f.friendID.user_id.id = :userId1 THEN f.friendID.friend_id.id ELSE f.friendID.user_id.id END as friendId 
            FROM FriendsEntity f 
            WHERE (f.friendID.user_id.id = :userId1 OR f.friendID.friend_id.id = :userId1) AND f.status = 'ACCEPTED' 
            ) AS user1Friends 
            INNER JOIN ( 
            SELECT CASE WHEN f.friendID.user_id.id = :userId2 THEN f.friendID.friend_id.id ELSE f.friendID.user_id.id END as friendId 
            FROM FriendsEntity f 
            WHERE (f.friendID.user_id.id = :userId2 OR f.friendID.friend_id.id = :userId2) AND f.status = 'ACCEPTED' 
            ) AS user2Friends 
            ON user1Friends.friendId = user2Friends.friendId
            """)
    Long countCommonFriends(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query("""
            SELECT CASE WHEN f.friendID.user_id.id = ?1 THEN f.friendID.friend_id.id ELSE f.friendID.user_id.id END 
            FROM FriendsEntity f 
            WHERE (f.friendID.user_id.id = ?1 OR f.friendID.friend_id.id = ?1) and f.status = 'ACCEPTED'
            """)
    List<Long> getFriends(Long id);

    @Query("""
            SELECT f
            FROM FriendsEntity f 
            WHERE (f.friendID.user_id.id = ?1 OR f.friendID.friend_id.id = ?1) and f.status = 'ACCEPTED'
            """)
    List<FriendsEntity> getFriends2(Long id);

    @Query(value = "SELECT f FROM FriendsEntity f JOIN f.friendID.friend_id ff JOIN f.friendID.user_id u WHERE (u.id = ?1 AND ff.id = ?2) OR (ff.id = ?1 AND u.id = ?2)")
    Optional<FriendsEntity> getFriendStatus(Long user_id1, Long user_id2);

    @Query(value = """
            SELECT new org.example.user_application.dto.interests.InterestStatsDto(i.value, COUNT(i.value) * 100.0 / (SELECT COUNT(*) FROM FriendsEntity f WHERE (f.friendID.user_id.id = ?1 OR f.friendID.friend_id.id = ?1) AND f.status='ACCEPTED'))
            FROM InterestEntity i
            JOIN i.userEntity u
            WHERE u.id IN (
            SELECT f.friendID.user_id.id
            FROM FriendsEntity f
            WHERE f.friendID.friend_id.id = ?1 AND f.status = 'ACCEPTED')
            OR u.id IN (
            SELECT f.friendID.friend_id.id
            FROM FriendsEntity f
            WHERE f.friendID.user_id.id = ?1 AND f.status = 'ACCEPTED')
            GROUP BY i.value
            """)
    List<InterestStatsDto> getFriendsInterestsByPercentage(Long userId);

    @Query(value = """
            SELECT new org.example.user_application.dto.friends.FriendsCountriesDto(u.country, COUNT(u.country))
            FROM UserEntity u
            WHERE u.id IN (
            SELECT f.friendID.user_id.id
            FROM FriendsEntity f
            WHERE f.friendID.friend_id.id = ?1 AND f.status = 'ACCEPTED')
            OR u.id IN (
            SELECT f.friendID.friend_id.id
            FROM FriendsEntity f
            WHERE f.friendID.user_id.id = ?1 AND f.status = 'ACCEPTED')
            GROUP BY u.country
            """)
    List<FriendsCountriesDto> getFriendsCountries(Long userId);

    @Query("SELECT COUNT(*)" +
            "FROM FriendsEntity f " +
            "WHERE (f.friendID.friend_id.id = :id OR f.friendID.user_id.id = :id) " +
            "AND f.status = 'ACCEPTED' " +
            "AND ((f.friendID.user_id.last_activity > :activeThreshold AND f.friendID.friend_id.id = :id) OR " +
            "(f.friendID.friend_id.last_activity > :activeThreshold AND f.friendID.user_id.id = :id))")
    Long getOnlineFriendsCount(@Param("id") Long id, @Param("activeThreshold") LocalDateTime activeThreshold);

    @Query(value = "SELECT count(*) FROM FriendsEntity f WHERE (f.friendID.friend_id.id = ?1 OR f.friendID.user_id.id = ?1) AND f.status = 'ACCEPTED'")
    Long getAllFriends(Long user_id);

    @Modifying
    @Transactional
    @Query(value ="DELETE FROM friends WHERE (user_id = ?1 AND friend_id = ?2) OR (user_id = ?2 AND friend_id = ?1)", nativeQuery = true)
    void removeFriend(Long userId1, Long userId2);

    @Modifying
    @Transactional
    @Query(value ="INSERT INTO friends (user_id, friend_id, status, last_update) VALUES (?1, ?2, 'PENDING', NOW())", nativeQuery = true)
    void createFriendRequest(Long userId1, Long userId2);

    @Modifying
    @Transactional
    @Query(value ="UPDATE friends SET status='ACCEPTED' WHERE (user_id=?1 AND friend_id=?2) OR (user_id = ?2 AND friend_id = ?1)", nativeQuery = true)
    void acceptFriendRequest(Long userId1, Long userId2);

    @Query(value = """
            SELECT
            CASE WHEN ((f.user_id=:user_id AND f.friend_id=:friend_id) OR (f.user_id=:friend_id AND f.friend_id=:user_id)) AND f.status='ACCEPTED' THEN 'ACCEPTED'
            WHEN (f.user_id=:user_id AND f.friend_id=:friend_id) AND f.status='PENDING' THEN 'PENDING'
            WHEN (f.user_id=:friend_id AND f.friend_id=:user_id) AND f.status='PENDING' THEN 'TO_ACCEPT'
            ELSE null END AS friendStatus
            FROM friends f ORDER BY friendStatus LIMIT 1
            """, nativeQuery = true)
    FriendStatus getUsersStatus(Long user_id, Long friend_id);

}
