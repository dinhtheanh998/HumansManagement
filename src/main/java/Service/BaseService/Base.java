package Service.BaseService;

import Connection.MyConnection;
import jdk.jfr.Name;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Base<T> implements IBase<T>{

    private Class<T> tClass;
    public Base(Class<T> tClass) {
        this.tClass = tClass;
    }


    @Override
    public List<T> getAll() {
        try{
            Connection connection = MyConnection.getMyConnection();
            String sql = String.format("SELECT * FROM %s", tClass.getSimpleName());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()){
                T t = tClass.newInstance();
                for (Field field : tClass.getDeclaredFields()){
                    String nameField = field.getAnnotation(Name.class).value();
                    System.out.println("Name field" + nameField);
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

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
//        List<String> listAnnotation = new ArrayList<>();
//        tClass.getSimpleName();
//        String sql = "SELECT * FROM " + tClass.getSimpleName();
//
//        System.out.println(tClass.getSimpleName());
    }

    @Override
    public boolean add() {
        return false;
    }

    @Override
    public boolean edit() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
