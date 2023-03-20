package Service.DepartmentService;

import Model.Department;
import Service.BaseService.Base;

public class DepartmentService extends Base<Department> implements IDepartmentService {

    public DepartmentService(Class<Department> departmentClass) {
        super(departmentClass);
    }
}
