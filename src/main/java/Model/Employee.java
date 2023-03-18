package Model;

import Service.BaseService.Base;
import jdk.jfr.Name;

import java.util.UUID;

public class Employee extends Base<Employee> {
    @Name("EmployeeId")

    private UUID id;

    @Name("tenNhanvien")
    private String name;

    @Name("tuoi")
    private int age;

    public Employee() {
        super(Employee.class);
    }

    public Employee(UUID id, String name, int age) {
        super(Employee.class);
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
