package Service.EmployeeService;

import DAO.EmployeeDAO.EmployeeDAO;
import Model.Employee;
import Service.BaseService.Base;

public class EmployeeService extends Base<Employee> implements IEmployeeService {

    public EmployeeService(Class<Employee> employeeClass) {
        super(employeeClass);
    }
}
