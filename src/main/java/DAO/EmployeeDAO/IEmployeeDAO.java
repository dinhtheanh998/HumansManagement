package DAO.EmployeeDAO;

import DAO.BaseDAO.IBaseDAO;
import Model.Employee;

import java.util.List;
import java.util.UUID;

public interface IEmployeeDAO extends IBaseDAO<Employee> {
    boolean DeleteBatch(String[] employeeCodes);


     boolean changeDepartmentID(String code, UUID newDepartmentID);
     List<Employee> filter(String keyword);
}
