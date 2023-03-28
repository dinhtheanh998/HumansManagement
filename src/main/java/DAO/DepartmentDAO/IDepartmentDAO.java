package DAO.DepartmentDAO;

import DAO.BaseDAO.IBaseDAO;
import Model.Department;

import java.util.UUID;

public interface IDepartmentDAO extends IBaseDAO<Department> {
    boolean checkDepartmentHasManager(UUID id);

    boolean changeDepartmentManager(UUID departmentId, String employeeId);
}
