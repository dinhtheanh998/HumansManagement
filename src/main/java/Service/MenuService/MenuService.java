package Service.MenuService;
import Service.EmployeeService.EmployeeService;
import Service.DepartmentService.DepartmentService;

import java.util.Scanner;

public class MenuService {
    public static void showMenu(){
        System.out.println("--------------------");
        System.out.println("1.Quản lý nhân viên");
        System.out.println("2.Quản lý phòng ban");
        System.out.println("3.Đăng xuất");
        System.out.println("--------------------");
    }
    public static void choose(int choice){
        switch (choice){
            case 1:
                int choiceEmp = menuEmployee();
                employeeMenuChoose(choiceEmp);
                break;
            case 2:
                int choiceDep =  menuDepartment();
                departmentMenuChoose(choiceDep);
                break;
            case 3:
                System.out.println("Đăng xuất thành công!");
                break;
            default:
                System.out.println("Vui lòng nhập lại!");
                break;
        }
    }
    /**
     * Menu lựa chọn quan ly nhân viên
     * @return 1 lựa chọn của người dùng
     * */
    public static int menuEmployee(){
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------");
        System.out.println("1.Thêm nhân viên");
        System.out.println("2.Sửa nhân viên");
        System.out.println("3.Xóa nhân viên");
        System.out.println("4.Xem danh sách nhân viên");
        System.out.println("5.Quay lại");
        System.out.println("--------------------");
        return sc.nextInt();

    }

    public static void employeeMenuChoose(int choice){
        switch (choice){
            case 1:
                EmployeeService.addEmployee();
                System.out.println("Bạn có muốn tiếp tục không? (Y/N)");
                Scanner sc = new Scanner(System.in);
                String choiceContinue = sc.nextLine();
                if(choiceContinue.equals("Y")){
                    employeeMenuChoose(choice);
                }else {
                    menuEmployee();
                }
                break;
            case 2:
                EmployeeService.editEmployee();
                break;
            case 3:
                EmployeeService.deleteEmployee();
                break;
            case 4:
//                EmployeeService.showEmployee();
                break;
            case 5:
                System.out.println("Quay lại");
                break;
            default:
                System.out.println("Vui lòng nhập lại!");
                break;
        }
    }


    public static int menuDepartment(){
        System.out.println("--------------------");
        System.out.println("1.Thêm phòng ban");
        System.out.println("2.Sửa phòng ban");
        System.out.println("3.Xóa phòng ban");
        System.out.println("4.Xem danh sách phòng ban");
        System.out.println("5.Quay lại");
        System.out.println("--------------------");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
    public static void  departmentMenuChoose(int choiceDep){
        switch (choiceDep) {
            case 1:
                DepartmentService.addDepartment();
                break;
            case 2:
                DepartmentService.editDepartment();
                break;
            case 3:
                DepartmentService.deleteDepartment();
                break;
            case 4:
        }
    }
}
