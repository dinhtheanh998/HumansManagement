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
    public boolean add(Class<?> pClass, boolean isUpdate) throws InstantiationException, IllegalAccessException {
        T tmp = tClass.newInstance();
        Scanner sc = new Scanner(System.in);
        for (Field field : tClass.getDeclaredFields()) {
            if (field.getType() == UUID.class) {
                field.setAccessible(true);
                field.set(tmp, UUID.randomUUID());
            }
            Label valueAnonaLabel = field.getAnnotation(Label.class);
            if (valueAnonaLabel == null) continue;
            if (field.getAnnotation(Description.class) != null) {
                // get all record from description.class
                printPclassFromAnonation(pClass, tmp, sc, field);

            } else {
                CustomAno myCustomAnn = field.getAnnotation(CustomAno.class);
                Validates myValidates = field.getAnnotation(Validates.class);
                if(customCheck(tmp)) {
                    System.out.println("Nhập: " + valueAnonaLabel.value());
                }else {
                    if(myCustomAnn != null) {
                        String unique = myCustomAnn.name();
                        if(unique.equalsIgnoreCase("unique")) {
                            continue;
                        }
                    }else {
                        System.out.println("Nhập: " + valueAnonaLabel.value());
                    }
                }
                field.setAccessible(true);
                String nextLine = sc.nextLine();
                if (myValidates != null) {
                    boolean check = Helper.validateInput(myValidates, nextLine, valueAnonaLabel.value(), tClass, isUpdate);
                    while (!check) {
                        System.out.println("Nhập lại: " + valueAnonaLabel.value());
                        nextLine = sc.nextLine();
                        check = Helper.validateInput(myValidates, nextLine, valueAnonaLabel.value(), tClass, isUpdate);
                    }
                    parseValueByType(tmp, nextLine, field);
                } else {
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

        return _baseDAO.add(tmp, isUpdate);
    }

    private void printPclassFromAnonation(Class<?> pClass, T tmp, Scanner sc, Field field) throws IllegalAccessException {
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
//                    printDataPClass(pClass, list, listSize);
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
            System.out.println("Nhập: " + field.getAnnotation(Label.class).value());
            String indexDepart = sc.nextLine();
            field.setAccessible(true);
            if (indexDepart.isEmpty()) {
                field.set(tmp, null);
            } else {
                UUID id = getUuid(indexDepart, list);
                if (id != null) {
                    field.set(tmp, id);
                }
            }
        }
    }

//    private void printDataPClass(Class<?> pClass, List<?> list, int listSize) throws IllegalAccessException {
//        for (int i = 0; i < listSize; i++) {
//            for (Field field2 : pClass.getDeclaredFields()) {
//                int length = 1;
//                String name = "";
//                CustomAno personAnnotation = field2.getAnnotation(CustomAno.class);
//                if (personAnnotation != null) {
//                    field2.setAccessible(true);
//                    length = personAnnotation.length();
//                    name = personAnnotation.name();
//                    if (name.equals("id"))
//                        System.out.printf("%-" + length + "s", i + 1);
//                    else
//                        System.out.printf("%-" + length + "s", field2.get(list.get(i)));
//                }
//            }
//            System.out.println();
//        }
//    }

    private static UUID getUuid(String sc, List<?> list) {
        String str = sc;
        if (str.isEmpty()) return null;
        Function<Object, UUID> function = o -> {
            try {
                Field f = o.getClass().getDeclaredField("id");
                f.setAccessible(true);
                return f.get(o) == null ? null : (UUID) f.get(o);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        };
        UUID id = function.apply(list.get(Integer.parseInt(str) - 1));
        return id;
    }

    private void parseValueByType(T tmp, String sc, Field field) throws IllegalAccessException {
        if (field.getType() == int.class) {
            field.set(tmp, Integer.parseInt(sc));
        } else if (field.getType() == Date.class) {
            if (sc.isEmpty()) {
                field.set(tmp, null);
                return;
            }
            DateFormat df = new SimpleDateFormat("yyy-MM-dd");
            Date newDate = null;
            try {
                newDate = df.parse(sc);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            field.set(tmp, newDate);
        } else if (field.getType() == BigDecimal.class) {
            if (sc.isEmpty() || sc.equals("0")) {
                field.set(tmp, null);
                return;
            } else {
                field.set(tmp, new BigDecimal(sc));
            }
        } else if (field.getType() == Integer.class) {
            if (sc.isEmpty() || sc.equals("0")) {
                field.set(tmp, null);
            } else {
                field.set(tmp, Integer.parseInt(sc));
            }
        } else {
            field.set(tmp, sc);
        }
    }

    @Override
    public boolean edit(Class<?> pClass, String code) {
        try {
            // lấy ra thông tin bản ghi theo code
            T tmp = _baseDAO.getByCode(code);
            if (tmp == null) {
                return false;
            }
            // lấy ra các field của class
            Field[] fields = tClass.getDeclaredFields();
            // lặp qua các field
            for (Field field : fields) {
                // kiểm tra xem field có annotation Label không
                if (field.getAnnotation(Label.class) != null) {
                    CustomAno myCustomAnn = field.getAnnotation(CustomAno.class);
                    // nếu có thì hiển thị ra màn hình
                    // lấy ra giá trị của field
                    field.setAccessible(true);
                    Object value = field.get(tmp);
                    Scanner sc = new Scanner(System.in);
                    String nextLine = "";
                    // hiển thị giá trị của field
                    // Kiểm tra xem field có annotation CustomAno name là unique không nếu có thì bỏ qua
                    if (field.getAnnotation(CustomAno.class) != null) {
                        String unique = myCustomAnn.name();
                        if (unique.equalsIgnoreCase("unique")) {
                            continue;
                        }
                    }

                    System.out.println(field.getAnnotation(Label.class).value());

                    if (field.getAnnotation(Description.class) != null) {
                        // get all record from description.class
                        printPclassFromAnonation(pClass, tmp, sc, field);
                    } else {
                        System.out.println("Giá trị cũ: " + value);
                        System.out.print("Nhập giá trị mới: ");
                        nextLine = sc.nextLine();
                        // kiểm tra xem người dùng có nhập gì không
                        if (!nextLine.isEmpty()) {
                            // nếu có thì thay đổi giá trị của field
                            parseValueByType(tmp, nextLine, field);
                        } else {
                            Validates myValidates = field.getAnnotation(Validates.class);
                            if (myValidates != null) {
                                boolean check = Helper.validateInput(myValidates, nextLine, field.getAnnotation(Label.class).value(), tClass, true);
                                while (!check) {
                                    System.out.print("Nhập lại: ");
                                    nextLine = sc.nextLine();
                                    check = Helper.validateInput(myValidates, nextLine, field.getAnnotation(Label.class).value(), tClass, true);
                                }
                            }
                        }
                    }

                }
            }
            return _baseDAO.update(tmp);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete() {
        try {
            System.out.println("Nhập mã cần xóa: ");
            Scanner sc = new Scanner(System.in);
            String code = sc.nextLine();
            boolean isSucessfull = _baseDAO.delete(code);
            if (isSucessfull) {
                System.out.println("Xóa thành công");
                return true;
            }
            return false;
//            else {
////                throw new Exception("Xóa thất bại");
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean customCheck(T t) {
        return true;
    }
}
