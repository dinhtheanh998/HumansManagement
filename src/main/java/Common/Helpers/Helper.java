package Common.Helpers;

import Common.Anonation.Validates;
import DAO.BaseDAO.BaseDAO;
import DAO.EmployeeDAO.EmployeeDAO;
import DAO.EmployeeDAO.IEmployeeDAO;
import Model.Employee;
import Service.EmployeeService.EmployeeService;

import java.lang.reflect.Field;
import java.util.List;

public class Helper {
    public static boolean validateInput(Validates validateName, String input, String fieldName, Class<?> tClass, boolean isUpdate) {
        if (validateName.value().length == 0) return true;
        for (int i = 0; i < validateName.value().length; i++) {
            String nameValue = validateName.value()[i].name();
            String value = validateName.value()[i].value();
            if (nameValue.equals("required") && input.isEmpty() && !isUpdate) {
                System.out.println(fieldName + " không được để trống");
                return false;
            }
            if (nameValue.equalsIgnoreCase("onlyOne")) {
                // DAO get all
                tClass.getSimpleName();
                if (!isUpdate) {
                    BaseDAO baseDAO = new BaseDAO(tClass);
                    Object rs = baseDAO.getByCode(input);
                    if (rs != null) {
                        System.out.println(fieldName + " đã tồn tại");
                        return false;
                    }
                }
                return true;
            }

            if (nameValue.equalsIgnoreCase("type") && !isUpdate) {
                String valueName = value;
                switch (valueName) {
                    case "int":
                        try {
                            Integer.parseInt(input);
                        } catch (Exception e) {
                            System.out.println(fieldName + " phải là số nguyên");
                            return false;
                        }
                        break;
                    case "double":
                        try {
                            Double.parseDouble(input);
                        } catch (Exception e) {
                            System.out.println(fieldName + " phải là số thực");
                            return false;
                        }
                        break;
                    case "float":
                        try {
                            Float.parseFloat(input);
                        } catch (Exception e) {
                            System.out.println(fieldName + " phải là số thực");
                            return false;
                        }
                        break;
                    case "long":
                        try {
                            Long.parseLong(input);
                        } catch (Exception e) {
                            System.out.println(fieldName + " phải là số nguyên");
                            return false;
                        }
                        break;
                    case "string":
                        break;
                    default:
                        break;
                }
            }
            if(!input.isEmpty()){
                if (nameValue.equals("min") && Integer.parseInt(input) < Integer.parseInt(value)) {
                    System.out.println(fieldName + " phải là 0: Nữ hoặc 1: Nam");
                    return false;
                }
                if (nameValue.equals("max") && Integer.parseInt(input) > Integer.parseInt(value)) {
                    System.out.println(fieldName + " phải là 0: Nữ hoặc 1: Nam");
                    return false;
                }

                if (nameValue.equals("minL") && input.length() < Integer.parseInt(value)) {
                    System.out.println(fieldName + " phải lớn hơn " + value);
                    return false;
                }
                if (nameValue.equals("maxL") && input.length() > Integer.parseInt(value)) {
                    System.out.println(fieldName + " phải nhỏ hơn " + value);
                    return false;
                }
                if (nameValue.equals("number")) {
                    return false;
                }
                if(nameValue.equals("email")){
                    if(!checkEmail(input)){
                        System.out.println(fieldName + " không đúng định dạng");
                        return false;
                    }else {
                        if(!isUpdate){
                            EmployeeService empS = new EmployeeService();
                            boolean rs = empS.getInfoByEmail(input);
                            if (rs) {
                                System.out.println(fieldName + " đã tồn tại");
                                return false;
                            }
                            return true;
                        }
                    }
                }
                if(nameValue.equals("phone")){
                    if(!checkPhone(input)){
                        System.out.println(fieldName + " không đúng định dạng");
                        return false;
                    }else {
                        if(!isUpdate){
                            EmployeeService empS = new EmployeeService();
                            boolean rs = empS.getInfoByPhone(input);
                            if (rs) {
                                System.out.println(fieldName + " đã tồn tại");
                                return false;
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }


    public static boolean checkPhone(String phone){
        if(!phone.matches("^[0-9]{10,11}$")){
            return false;
        }
        return true;
    }

    public static boolean checkEmail (String email){
        if(!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$")){
            return false;
        }
        return true;
    }
}
