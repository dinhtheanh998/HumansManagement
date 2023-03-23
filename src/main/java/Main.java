import Model.Employee;
import Service.EmployeeService.EmployeeService;
import Service.MenuService.MenuService;
import jdk.jfr.Name;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

//        EmployeeService employeeService = new EmployeeService(Employee.class);
//        List<Employee> list = employeeService.getAll();

//        int length = emp.getClass().getDeclaredFields().length;
//        for (int i = 0; i < length; i++){
//            System.out.println(emp.getClass().getDeclaredFields()[i].getAnnotation(Name.class).value());
//        }

        boolean isLogin = false;
        int count = 0;
//        BigDecimal hehe = EmployeeService.getTax(new BigDecimal(91000000));
//        System.out.println(hehe);
        while (!isLogin){
            if(count > 0)
                System.out.println("Sai thông tin đăng nhập, vui lòng nhập lại!");
            isLogin = Login();
            count++;
            if(count == 3){
                System.out.println("Bạn đã đăng nhập sai 3 lần!");
                break;
            }
        }
        if(isLogin){
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            while (choice != 3){
                MenuService.showMenu();
                System.out.print("Nhập lựa chọn: ");
                choice = sc.nextInt();
                MenuService.choose(choice);
            }
        }

    }

    public static boolean Login(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tài khoản: ");
        String username = sc.nextLine();
        System.out.print("\nNhập mật khẩu: ");
        String password = sc.nextLine();
        if(username.equals("admin") && password.equals("admin")){
            System.out.println("Đăng nhập thành công!");
            return true;
        }
        return false;
    }

    // calc tax for employee

}
