import Model.Employee;
import Service.MenuService.MenuService;
import jdk.jfr.Name;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Employee emp = new Employee(UUID.randomUUID(), "Nguyen Van A", 20);
        emp.getAll();
//        int length = emp.getClass().getDeclaredFields().length;
//        for (int i = 0; i < length; i++){
//            System.out.println(emp.getClass().getDeclaredFields()[i].getAnnotation(Name.class).value());
//        }

//        boolean isLogin = false;
//        int count = 0;
//        while (!isLogin){
//            System.out.println("Sai thông tin đăng nhập, vui lòng nhập lại!");
//            isLogin = Login();
//            count++;
//            if(count == 3){
//                System.out.println("Bạn đã đăng nhập sai 3 lần!");
//                break;
//            }
//        }
//        if(isLogin){
//            Scanner sc = new Scanner(System.in);
//            int choice = 0;
//            while (choice != 3){
//                MenuService.showMenu();
//                System.out.print("Nhập lựa chọn: ");
//                choice = sc.nextInt();
//                MenuService.choose(choice);
//            }
//        }

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
}
