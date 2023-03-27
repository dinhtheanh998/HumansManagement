package Service.EmployeeService;

import Model.Employee;
import Service.BaseService.IBase;

import java.math.BigDecimal;
import java.util.List;

public interface IEmployeeService extends IBase<Employee> {

    boolean DeleteBatch();
//    boolean getInfoByEmail(String email);

//    boolean getInfoByPhone(String phone);

    boolean changeDepartmentID();

    List<Employee> filter();
}
