package DAO.DepartmentDAO;

import DAO.BaseDAO.BaseDAO;
import Model.Department;

public class DepartmentDAO extends BaseDAO<Department> implements IDepartmentDAO {
    public DepartmentDAO(Class<Department> departmentClass) {
        super(departmentClass);
    }
}
