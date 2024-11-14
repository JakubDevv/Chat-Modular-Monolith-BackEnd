package org.example.user_infrastructure.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "interests")
public class InterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "fk_interests_users")
    private UserEntity userEntity;

    public InterestEntity() {
    }

    public InterestEntity(String value, UserEntity userEntity) {
        this.value = value;
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
