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
    public boolean add(T t) {
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
                    preparedStatement.setString(index, field.get(t).toString());
                }else {
                    preparedStatement.setObject(index, field.get(t));
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
        return false;
    }

    @Override
    public boolean delete(T t) {
        return false;
    }

    @Override
    public T get(UUID id) {
        return null;
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
            while (resultSet.next()){
                for(Field field: tClass.getDeclaredFields()){
                    if(field.getAnnotation(Name.class) == null) continue;
                    String nameField = field.getAnnotation(Name.class).value();
                    field.setAccessible(true);
                    if(field.getType() == UUID.class){
                        UUID id = UUID.fromString(resultSet.getString(nameField));
                        field.set(t, id);
                    }else {
                        field.set(t, resultSet.getObject(nameField));
                    }
                }
            }
            return t;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
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
                        UUID id = UUID.fromString(resultSet.getString(nameField));
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
}
