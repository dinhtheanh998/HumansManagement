package DAO.DepartmentDAO;

import DAO.BaseDAO.BaseDAO;
import DAO.BaseDAO.IBaseDAO;
import Model.Department;
import Connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class DepartmentDAO extends BaseDAO<Department> implements IDepartmentDAO {
    public DepartmentDAO(Class<Department> departmentClass) {
        super(departmentClass);
    }

    @Override
    public boolean checkDepartmentHasManager(UUID id) {
        try {
            if(id == null) return false;
            Connection connection = MyConnection.getMyConnection();
            String sql = "SELECT * FROM department d LEFT JOIN employee e ON d.ID = e.DepartmentID WHERE d.ID = ? AND e.Is_MngDepartment = 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet =  preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }else {
               return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
