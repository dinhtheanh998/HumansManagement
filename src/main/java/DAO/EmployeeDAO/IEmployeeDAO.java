package DAO.EmployeeDAO;

import DAO.BaseDAO.IBaseDAO;
import Model.Employee;

import java.util.List;

public interface IEmployeeDAO extends IBaseDAO<Employee> {
    boolean DeleteBatch(String[] employeeCodes);

     Boolean getInfoByEmail(String email);
     Boolean getInfoByPhone(String phone);
}
