package Model;

import jdk.jfr.Name;

import java.util.UUID;

public class Department {
    @Name("id")
    private UUID id;

    @Name("code")
    private String code;

    @Name("name")
    private String name;

    @Name("discription")
    private String discription;

    public Department() {
    }

    public Department(UUID id, String code, String name, String discription) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.discription = discription;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", discription='" + discription + '\'' +
                '}';
    }
}