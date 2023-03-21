package Service.BaseService;

import Common.Anonation.CustomAno;
import Common.Anonation.Validates;
import Common.Helpers.Helper;
import DAO.BaseDAO.BaseDAO;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

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
    public boolean add(Class<?> pClass) throws InstantiationException, IllegalAccessException {
        T tmp = tClass.newInstance();
        Scanner sc = new Scanner(System.in);
        for (Field field : tClass.getDeclaredFields()) {
            if (field.getType() == UUID.class) {
                field.setAccessible(true);
                field.set(tmp, UUID.randomUUID());
            }
            if (field.getAnnotation(Label.class) == null) continue;
            if (field.getAnnotation(Description.class) != null) {
                // get all record from description.class
                if (pClass != null) {
                    // get all record from pClass
                    List<?> list = new Base<>(pClass).getAll();
                    int listSize = list.size();
                    String str = "";
                    for (Field field1 : pClass.getDeclaredFields()) {
                        CustomAno personAnnotation = (CustomAno) field1.getAnnotation(CustomAno.class);
                        if (personAnnotation != null) {
                            str += "%-" + personAnnotation.length() + "s";
                        }
                    }
                    System.out.printf(str, "STT", "Mã", "Tên", "Mô tả");
                    System.out.println();
                    printDataPClass(pClass, list, listSize);
                    System.out.println("Nhập: " + field.getAnnotation(Label.class).value());
                    field.setAccessible(true);
                    UUID id = getUuid(sc, list);
                    field.set(tmp, id);
                }

            } else {
                CustomAno myCustomAnn = field.getAnnotation(CustomAno.class);
                Validates myValidates = field.getAnnotation(Validates.class);
                System.out.println("Nhập: " + field.getAnnotation(Label.class).value());
                field.setAccessible(true);
                String nextLine = sc.nextLine();

                if(myValidates != null){
                    boolean check = Helper.validateInput(myValidates,nextLine, field.getAnnotation(Label.class).value());
                    while (!check){
                        System.out.println("Nhập lại: " + field.getAnnotation(Label.class).value());
                        nextLine = sc.nextLine();
                        check = Helper.validateInput(myValidates,nextLine, field.getAnnotation(Label.class).value());
                    }
                    parseValueByType(tmp, nextLine, field);
                }else {
                    if (myCustomAnn != null) {
                        String unique = myCustomAnn.name();
                        if (nextLine.isEmpty() && unique.equals("unique")) {
                            field.set(tmp, null);
                        } else {
                            parseValueByType(tmp, nextLine, field);
                        }
                    } else {
                        parseValueByType(tmp, nextLine, field);
                    }
                }
            }

        }

        return _baseDAO.add(tmp);
    }

    private void printDataPClass(Class<?> pClass, List<?> list, int listSize) throws IllegalAccessException {
        for (int i = 0; i < listSize; i++) {
            for (Field field2 : pClass.getDeclaredFields()) {
                int length = 1;
                String name = "";
                CustomAno personAnnotation = field2.getAnnotation(CustomAno.class);
                if (personAnnotation != null) {
                    field2.setAccessible(true);
                    length = personAnnotation.length();
                    name = personAnnotation.name();
                    if (name.equals("id"))
                        System.out.printf("%-" + length + "s", i + 1);
                    else
                        System.out.printf("%-" + length + "s", field2.get(list.get(i)));
                }
            }
            System.out.println();
        }
    }

    private static UUID getUuid(Scanner sc, List<?> list) {
        Function<Object, UUID> function = o -> {
            try {
                Field f = o.getClass().getDeclaredField("id");
                f.setAccessible(true);
                return  f.get(o) == null ? null : (UUID) f.get(o);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        };
        UUID id = function.apply(list.get(Integer.parseInt(sc.nextLine()) - 1));
        return id;
    }

    private void parseValueByType(T tmp, String sc, Field field) throws IllegalAccessException {
        if(field.getType() == int.class){
            field.set(tmp, Integer.parseInt(sc));
        }else if(field.getType() == Date.class) {
            DateFormat df = new SimpleDateFormat("yyy-MM-dd");
            Date newDate = null;
            try {
                newDate = df.parse(sc);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            field.set(tmp, newDate);
        }else if(field.getType() == BigDecimal.class){
            field.set(tmp, new BigDecimal(sc));
        }
        else {
            field.set(tmp, sc);
        }
    }

    @Override
    public boolean edit(String code) {
        try{
            // lấy ra thông tin bản ghi theo code
             T tmp = _baseDAO.getByCode(code);
            // lấy ra các field của class
            System.out.println(tmp);


            // Hiển thị thông tin để người dùng sửa
            // lấy thông tin mới từ người dùng nhập vào
                // Kiểm tra thông tin mới có hợp lệ không
                // Nếu người dùng để trống thì vẫn giữ nguyên thông tin của bản ghi trước
                // Nếu người dùng nhập vào thì thay đổi thông tin của bản ghi
            // lưu thông tin mới vào database

            // đóng kết nối

            // kiểm tra bản ghi ảnh hưởng nếu lớn hơn 0 thì trả về true
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
