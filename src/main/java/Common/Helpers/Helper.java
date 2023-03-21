package Common.Helpers;

import Common.Anonation.Validates;

import java.lang.reflect.Field;
import java.util.List;

public class Helper {
    public static boolean validateInput (Validates validateName, String input, String fieldName){
        if (validateName.value().length == 0) return true;
        for (int i = 0; i < validateName.value().length; i++) {
            if (validateName.value()[i].name().equals("required") && input.isEmpty()) {
                System.out.println(fieldName + " không được để trống");
                return false;
            }
            if (validateName.value()[i].name().equals("min") && input.length() < Integer.parseInt(validateName.value()[i].value())) {
                System.out.println(fieldName + " phải lớn hơn " + validateName.value()[i].value());
                return false;
            }
            if (validateName.value()[i].name().equals("max") && input.length() > Integer.parseInt(validateName.value()[i].value())) {
                System.out.println(fieldName + " phải nhỏ hơn " + validateName.value()[i].value());
                return false;
            }
            if(validateName.value()[i].name().equals("number")){
                return false;
            }
        }
        return true;
    }
}
