package Service.DepartmentService;

import DAO.DepartmentDAO.DepartmentDAO;
import Model.Department;
import Service.BaseService.Base;

import java.util.UUID;

public class DepartmentService extends Base<Department> implements IDepartmentService {
    private DepartmentDAO _departmentDAO;

    public DepartmentService() {
        super(Department.class);
        _departmentDAO = new DepartmentDAO(Department.class);
    }
    public DepartmentService(Class<Department> departmentClass) {
        super(departmentClass);
    }

    @Override
    public boolean checkDepartmentHasManager(UUID id) {
        return _departmentDAO.checkDepartmentHasManager(id);
    }
}
