package DAO.BaseDAO;

import Connection.MyConnection;
import jdk.jfr.Name;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseDAO<T> implements IBaseDAO<T> {
    private Class<T> tClass;
    public BaseDAO(Class<T> tClass) {
        this.tClass = tClass;
    }
    @Override
    public boolean add(T t, boolean isUpdate) {
        try {
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("INSERT INTO %s (", tClass.getSimpleName());
            // INsert into Department (id, code, name, discription) values (?,?,?,?)

            String value = "VALUES (";
            for (Field field : tClass.getDeclaredFields()){
                if(field.getAnnotation(Name.class) == null) continue;
                String nameField = field.getAnnotation(Name.class).value();
                sql += nameField + ",";
                value += "?,";
            }
            sql = sql.substring(0, sql.length() - 1) + ")";
            value = value.substring(0, value.length() - 1) + ")";
            sql += value;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            for (Field field : tClass.getDeclaredFields()){
                if(field.getAnnotation(Name.class) == null) continue;
                // cấp quyền truy cập
                field.setAccessible(true);

                if(field.getType() == UUID.class){
                    preparedStatement.setString(index, field.get(t) != null ? field.get(t).toString() : null);
                }else {
                    preparedStatement.setObject(index, field.get(t) != null ? field.get(t) : null);
                }
                index++;
            }
            int result = preparedStatement.executeUpdate();
            // close connection
            connection.close();
            return result > 0;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean update(T t) {
        try {
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("UPDATE %s SET ", tClass.getSimpleName());
            // update Department set code = ?, name = ?, discription = ? where id = ?
            for (Field field : tClass.getDeclaredFields()){
                if(field.getAnnotation(Name.class) == null) continue;
                String nameField = field.getAnnotation(Name.class).value();
                sql += nameField + " = ?,";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += " WHERE id = ";

            // get id of t
            for (Field field : tClass.getDeclaredFields()){
                field.setAccessible(true);
                if(field.getAnnotation(Name.class) == null) continue;
                if(field.getName().equalsIgnoreCase("id")){
                    sql += field.get(t).toString() != null ? "'"+field.get(t).toString()+"'" : null;
                }
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            for (Field field : tClass.getDeclaredFields()){
                if(field.getAnnotation(Name.class) == null) continue;
                // cấp quyền truy cập
                field.setAccessible(true);
                if(field.getType() == UUID.class){
                    preparedStatement.setString(index, field.get(t) != null ? field.get(t).toString() : null);
                }else {
                    preparedStatement.setObject(index, field.get(t) != null ? field.get(t) : null);
                }
                index++;
            }
//            System.out.println(preparedStatement);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String code) {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("DELETE FROM %s WHERE code = ?", tClass.getSimpleName());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            int result = preparedStatement.executeUpdate();
            connection.close();
            return result > 0;
        }catch(Exception SQLIntegrityConstraintViolationException){
            throw new RuntimeException("Không thể xóa vì có dữ liệu liên quan");
        }
    }

    @Override
    public T get(UUID id) {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("SELECT * FROM %s WHERE id = ?", tClass.getSimpleName());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            T t = tClass.newInstance();
            if (resultSet.next()){
                for(Field field: tClass.getDeclaredFields()){
                    if(field.getAnnotation(Name.class) == null) continue;
                    String nameField = field.getAnnotation(Name.class).value();
                    field.setAccessible(true);
                    if(field.getType() == UUID.class){
                        if(resultSet.getString(nameField) == null) {
                            field.set(t, null);
                        }else {
                            UUID id2 = UUID.fromString(resultSet.getString(nameField));
                            field.set(t, id2);
                        }
                    }else {
                        field.set(t, resultSet.getObject(nameField));
                    }
                }
            }else {
                return null;
            }
            return t;
        }catch (Exception e){
                return null;
        }
    }

    @Override
    public T getByCode(String code) {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("SELECT * FROM %s WHERE code = ?", tClass.getSimpleName());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            T t = tClass.newInstance();
            if (resultSet.next()){
                for(Field field: tClass.getDeclaredFields()){
                    if(field.getAnnotation(Name.class) == null) continue;
                    String nameField = field.getAnnotation(Name.class).value();
                    field.setAccessible(true);
                    if(field.getType() == UUID.class){
                        if(resultSet.getString(nameField) == null) {
                            field.set(t, null);
                        }else {
                            UUID id = UUID.fromString(resultSet.getString(nameField));
                            field.set(t, id);
                        }
                    }else {
                        field.set(t, resultSet.getObject(nameField));
                    }
                }
            }else {
                return null;
            }
            return t;
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<T> getAll() {
        try {
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("SELECT * FROM %s", tClass.getSimpleName());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()){
                T t = tClass.newInstance();
                for (Field field : tClass.getDeclaredFields()){
                    if(field.getAnnotation(Name.class) == null) continue;
                    String nameField = field.getAnnotation(Name.class).value();
                    field.setAccessible(true);
                    if(field.getType() == UUID.class){
                        UUID id = resultSet.getString(nameField) != null ? UUID.fromString(resultSet.getString(nameField)) : null;
                        field.set(t, id);
                    }else {
                        field.set(t, resultSet.getObject(nameField));
                    }
                }
                list.add(t);
            }
            // close connection
            connection.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean getByEmail(String email) {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("SELECT * FROM %s WHERE email = ?", tClass.getSimpleName());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean getByPhone(String phone) {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("SELECT * FROM %s WHERE phone = ?", tClass.getSimpleName());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
