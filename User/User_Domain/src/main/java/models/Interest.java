package models;


public class Interest {

    private Long id;

    private String value;

    public Interest() {
    }

    public Interest(String value) {
        this.value = value;
    }

    public Interest(Long id, String value) {
        this.value = value;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
