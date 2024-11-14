package org.example.user_infrastructure.repositories;

import org.example.user_infrastructure.entities.InterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestsRepositoryDB extends JpaRepository<InterestEntity, Integer> {

    @Query("SELECT i FROM InterestEntity i JOIN i.userEntity u WHERE u.id = ?1")
    List<InterestEntity> findAllByUserId(Long id);
}
