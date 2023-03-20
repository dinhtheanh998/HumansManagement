package DAO.EmployeeDAO;

import DAO.BaseDAO.BaseDAO;
import Model.Employee;

public class EmployeeDAO extends BaseDAO<Employee> implements IEmployeeDAO {
    public EmployeeDAO(Class<Employee> employeeClass) {
        super(employeeClass);
    }
}
