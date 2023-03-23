package DAO.EmployeeDAO;

import DAO.BaseDAO.BaseDAO;
import Model.Employee;
import Connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class EmployeeDAO extends BaseDAO<Employee> implements IEmployeeDAO {
    public EmployeeDAO(Class<Employee> employeeClass) {
        super(employeeClass);
    }

    @Override
    public boolean DeleteBatch(String[] employeeCodes) {
        try {
            Connection connection = MyConnection.getMyConnection();
            String sql = "DELETE FROM Employee WHERE code IN (";
            int length = employeeCodes.length;
            for (int i = 0; i < length; i++) {
                sql += "?";
                if (i < length - 1) {
                    sql += ",";
                }
            }
            sql += ")";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < length; i++) {
                preparedStatement.setString(i + 1, employeeCodes[i]);
            }
            int result = preparedStatement.executeUpdate();
            connection.close();
            return result > 0;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
