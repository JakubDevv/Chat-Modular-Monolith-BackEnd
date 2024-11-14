package models;

import java.time.LocalDateTime;

public class Group {

    private Long id;

    private String name;

    private LocalDateTime created;

    private boolean isduo;

    public Group(String name, boolean isduo) {
        this.name = name;
        this.created = LocalDateTime.now();
        this.isduo = isduo;
    }

    public Group(Long id, String name, LocalDateTime created, boolean isduo) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.isduo = isduo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isIsduo() {
        return isduo;
    }
}
