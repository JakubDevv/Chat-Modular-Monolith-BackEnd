package models;

import enums.UserStatus;
import vo.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class User {

    private UserId id;

    private String username;

    private Email email;

    private FullName fullName;

    private HashedPassword password;

    private Localization localization;

    private LocalDateTime created;

    private HexColor bgColor;

    private LocalDateTime birth;

    private LocalDateTime last_activity;

    public User() {
    }

    public User(String username, String firstName, String lastName, String password, String country, String city) {
        this.username = username;
        this.fullName = new FullName(firstName, lastName);
        this.password = new HashedPassword(password);
        this.localization = new Localization(country, city);
        this.created = LocalDateTime.now();
        this.last_activity = LocalDateTime.now();
    }

    public User(Long id, String email, String firstName, String lastName, String username, String country, String city, LocalDateTime created, String bgColor, LocalDateTime birth, LocalDateTime last_activity, String password) {
        this.id = new UserId(id);
        this.email = new Email(email);
        this.username = username;
        this.fullName = new FullName(firstName, lastName);
        this.password = new HashedPassword(password);
        this.localization = new Localization(country, city);
        this.created = created;
        this.bgColor = new HexColor(bgColor);
        this.birth = birth;
        this.last_activity = last_activity;
    }

    public void updateLastActivity(){
        this.last_activity = LocalDateTime.now();
    }

    public UserStatus getUserStatus(){
        return (Duration.between(last_activity, LocalDateTime.now()).toMinutes() < 2) ? UserStatus.ONLINE : UserStatus.OFFLINE;
    }

    public UserId getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public FullName getFullName() {
        return fullName;
    }

    public HashedPassword getPassword() {
        return password;
    }

    public Localization getLocalization() {
        return localization;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public HexColor getBgColor() {
        return bgColor;
    }

    public int getAge() {
        return Period.between(birth.toLocalDate(), LocalDate.now()).getYears();
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public LocalDateTime getLast_activity() {
        return last_activity;
    }

    public String getUsername() {
        return username;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = new HexColor(bgColor);
    }
}
