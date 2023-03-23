package DAO.EmployeeDAO;

import DAO.BaseDAO.IBaseDAO;
import Model.Employee;

import java.util.List;

public interface IEmployeeDAO extends IBaseDAO<Employee> {
    public boolean DeleteBatch(String[] employeeCodes);
}
