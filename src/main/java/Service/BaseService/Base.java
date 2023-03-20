package Service.BaseService;

import DAO.BaseDAO.BaseDAO;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Base<T> implements IBase<T> {

    private Class<T> tClass;
    private BaseDAO<T> _baseDAO;

    public Base(Class<T> tClass) {
        this.tClass = tClass;
        _baseDAO = new BaseDAO<>(tClass);
    }



    @Override
    public List<T> getAll() {
        return _baseDAO.getAll();
    }

    @Override
    public  boolean add(Class<?> pClass) throws InstantiationException, IllegalAccessException {
        T tmp = tClass.newInstance();
        Scanner sc = new Scanner(System.in);
        for (Field field : tClass.getDeclaredFields()){
            if(field.getType() == UUID.class){
                field.setAccessible(true);
                field.set(tmp, UUID.randomUUID());
            }
            if(field.getAnnotation(Label.class) == null) continue;
            if(field.getAnnotation(Description.class) != null) {
                // get all record from description.class
                if(pClass != null){
                   // get all record from pClass
                    List<?> list = new Base<>(pClass).getAll();
                    int listSize = list.size();
                    System.out.printf("%-40s %-40s %-40s %-40s", "STT", "Mã", "Tên", "Mô tả" );
                    for(int i = 0; i < listSize; i++){
                        System.out.println();
                        for (Field field2 : pClass.getDeclaredFields()){
                            field2.setAccessible(true);
//                            if(field2.getAnnotation(Name.class) == null) continue;
                            System.out.printf("%-40s" ,field2.get(list.get(i)));
                        }
                        System.out.println();
                    }
                }
            }
            System.out.println("Nhập: " + field.getAnnotation(Label.class).value());
            field.setAccessible(true);
            field.set(tmp, sc.nextLine());
        }

        return _baseDAO.add(tmp);
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
