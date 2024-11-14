package org.example.user_infrastructure.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String first_name;

    private String last_name;

    private String password;

    private String country;

    private String city;

    private LocalDateTime created;

    private String bgcolor;

    private LocalDateTime birth;

    private LocalDateTime last_activity;

    public UserEntity(Long id, String email, String username, String first_name, String last_name, String password, String country, String city, LocalDateTime created, String bgcolor, LocalDateTime birth, LocalDateTime last_activity) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.country = country;
        this.city = city;
        this.created = created;
        this.bgcolor = bgcolor;
        this.birth = birth;
        this.last_activity = last_activity;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public LocalDateTime getLast_activity() {
        return last_activity;
    }
}
