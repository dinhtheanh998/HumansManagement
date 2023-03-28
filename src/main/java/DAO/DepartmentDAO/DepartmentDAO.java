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
            if(id == null) return true;
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

    @Override
    public boolean changeDepartmentManager(UUID departmentId, String code) {
        Connection connection = null;
        try {
            if(departmentId == null || code == null) return false;
            connection = MyConnection.getMyConnection();
            connection.getTransactionIsolation();
            connection.setAutoCommit(false);
            String sql = "UPDATE employee SET Is_MngDepartment = NULL WHERE departmentid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, departmentId.toString());
            int rowAffected =  preparedStatement.executeUpdate();
            if(rowAffected >0){
                sql = "UPDATE employee SET Is_MngDepartment = 1 WHERE code = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, code);
                int row2affected =  preparedStatement.executeUpdate();
                if(row2affected > 0) {
                    connection.commit();
                    System.out.println("Nhân viên " + code + " đã được chuyển thành trưởng phòng");
                }else {
                    connection.rollback();
                    System.out.println("Thất bại");
                    return false;
                }
            }else {
                connection.rollback();
                System.out.println("Thất bại");
            }
            return true;
        } catch (Exception e) {
            if(connection != null)
                try {
                    connection.rollback();
                    return false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return false;
                }
            System.out.println("Lỗi: Vui lòng liên hệ SS");
            return false;
        }
    }
}
