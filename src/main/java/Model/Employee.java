package Model;

import Service.BaseService.Base;
import jdk.jfr.Name;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Employee  {
    @Name("ID")
    private UUID id;
    @Name("Code")
    private String code;
    @Name("FullName")
    private String name;

    @Name("Gender")
    private int gender;
    @Name("Email")
    private String email;
    @Name("Salary")
    private BigDecimal salary;
    @Name("DateOfBirth")
    private Date dateOfBirth;
    @Name("Address")
    private String address;

    @Name("IdentityNumber")
    private String identityNumber;

    @Name("DepartmentID")
    private UUID departmentID;

    private String departmentName;

    @Name("Phone")
    private String phone;

    @Name("Is_MngDepartment")
    private Integer isManager;


    public Employee() {
    }

    public Employee(Class<Employee> employeeClass, UUID id, String code, String name, int gender,
                    String email, BigDecimal salary, Date dateOfBirth, String address, String identityNumber,
                    UUID departmentID, String phone, int isManager) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.identityNumber = identityNumber;
        this.departmentID = departmentID;
        this.phone = phone;
        this.isManager = isManager;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public UUID getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(UUID departmentID) {
        this.departmentID = departmentID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", departmentID=" + departmentID +
                ", phone='" + phone + '\'' +
                ", isManager=" + isManager +
                '}';
    }
}
