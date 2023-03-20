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
