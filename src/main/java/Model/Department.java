package Model;

import Common.Anonation.CustomAno;
import jdk.jfr.Name;
import jdk.jfr.Label;

import java.util.UUID;

public class Department {
    @Name("id")
    private UUID id;

    @Name("code")
    @Label("Mã phòng ban")
    @CustomAno(name = "code", length = 10)
    private String code;

    @Name("name")
    @Label("Tên phòng ban")
    private String name;

    @Name("discription")
    @Label("Mô tả")
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