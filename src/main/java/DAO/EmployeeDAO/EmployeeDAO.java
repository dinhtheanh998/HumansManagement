package DAO.EmployeeDAO;

import DAO.BaseDAO.BaseDAO;
import Model.Employee;
import Connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

//    @Override
//    public Boolean getInfoByEmail(String email) {
//        try{
//            Connection connection = MyConnection.getMyConnection();
//            String sql = "SELECT email FROM Employee WHERE email = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, email);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                return true;
//            }else {
//                return false;
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    @Override
//    public Boolean getInfoByPhone(String phone) {
//        try{
//            Connection connection = MyConnection.getMyConnection();
//            String sql = "SELECT phone FROM Employee WHERE phone = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, phone);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                return true;
//            }else {
//                return false;
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }

    @Override
    public boolean changeDepartmentID(String code, UUID newDepartmentID) {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = "UPDATE Employee SET departmentID = ? WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newDepartmentID.toString());
            preparedStatement.setString(2, code);
            int result = preparedStatement.executeUpdate();
            connection.close();
            return result > 0;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Employee> filter(String keyword) {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = "SELECT * FROM Employee WHERE code LIKE ? OR fullName LIKE ? OR email LIKE ? OR phone LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employees = this.getList(resultSet);
            connection.close();
            return employees;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<Employee> getList(ResultSet resultSet) {
        try{
            List<Employee> employees = new ArrayList<>();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setCode(resultSet.getString("code"));
                employee.setName(resultSet.getString("fullName"));
                employee.setAddress(resultSet.getString("address"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setEmail(resultSet.getString("email"));
                employee.setIdentityNumber(resultSet.getString("identityNumber"));
                employee.setDateOfBirth(resultSet.getDate("dateOfBirth"));
                employee.setSalary(resultSet.getBigDecimal("salary") != null ? resultSet.getBigDecimal("salary") : null);
                employee.setDepartmentID(resultSet.getString("departmentID") != null ? UUID.fromString(resultSet.getString("departmentID")) : null);
                employees.add(employee);
            }
            return employees;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }

    }

}
