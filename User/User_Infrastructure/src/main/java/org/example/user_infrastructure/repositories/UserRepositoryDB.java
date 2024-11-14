package org.example.user_infrastructure.repositories;

import jakarta.transaction.Transactional;
import org.example.user_infrastructure.entities.InterestEntity;
import org.example.user_infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryDB extends JpaRepository<UserEntity, Long> {

    @Query("SELECT i FROM InterestEntity i JOIN i.userEntity u WHERE u.id = ?1")
    List<InterestEntity> getInterestsByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM InterestEntity n WHERE n.id=?1")
    void deleteInterestById(Long id);

    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    Optional<UserEntity> findUserByUsername(String username);

}
