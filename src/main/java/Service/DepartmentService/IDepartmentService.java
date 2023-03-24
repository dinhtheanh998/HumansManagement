package Service.DepartmentService;

import Model.Department;
import Service.BaseService.IBase;

import java.util.UUID;

public interface IDepartmentService extends IBase<Department> {

    boolean checkDepartmentHasManager(UUID id);
}
