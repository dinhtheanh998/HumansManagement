package Service.MenuService;
import Model.Department;
import Model.Employee;
import Service.EmployeeService.EmployeeService;
import Service.DepartmentService.DepartmentService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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
                EmployeeService employeeService = new EmployeeService(Employee.class);
                try {
                    employeeMenuChoose(choiceEmp,employeeService);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
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

    public static void employeeMenuChoose(int choice, EmployeeService employeeService) throws InstantiationException, IllegalAccessException {
        switch (choice){
            case 1:
                employeeService.add(Department.class);
                System.out.println("Bạn có muốn tiếp tục không? (Y/N)");
                Scanner sc = new Scanner(System.in);
                String choiceContinue = sc.nextLine();
                if(choiceContinue.equals("Y")){
                    employeeMenuChoose(choice, employeeService);
                }else {
                    menuEmployee();
                }
                break;
            case 2:
//                EmployeeService.editEmployee();
                break;
            case 3:
//                EmployeeService.deleteEmployee();
                break;
            case 4:
                List<Employee> lstEmp = employeeService.getAll();
                lstEmp.forEach(System.out::println);
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
    public static void  departmentMenuChoose(int choiceDep) {
        DepartmentService departmentService = new DepartmentService(Department.class);
        switch (choiceDep) {
            case 1:
                List<Department> lstDepartment =  departmentService.getAll();
                lstDepartment.forEach(System.out::println);
                break;
            case 2:
                Department department = new Department(UUID.randomUUID(),"PB01","Phòng ban 1","Phòng ban 1");
                try{
                    departmentService.add(null);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
//                departmentService.add();
//                departmentService.editDepartment();
                break;
            case 3:
//                departmentService.deleteDepartment();
                break;
            case 4:
        }
    }
}
