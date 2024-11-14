package org.example.messages_infrastructure.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime created;

    private boolean isduo;

    public GroupEntity(Long id, String name, LocalDateTime created, boolean isduo) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.isduo = isduo;
    }

    public GroupEntity() {
    }
}
